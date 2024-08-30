package org.acme.employeescheduling.solver;

import static ai.timefold.solver.core.api.score.stream.Joiners.equal;
import static ai.timefold.solver.core.api.score.stream.Joiners.lessThanOrEqual;
import static ai.timefold.solver.core.api.score.stream.Joiners.overlapping;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ai.timefold.solver.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.*;

import org.acme.employeescheduling.domain.Setting;
import org.acme.employeescheduling.domain.ShiftAssignment;
import org.acme.employeescheduling.domain.ShiftType;
//import org.apache.commons.math3.util.Pair;
import org.apache.commons.lang3.tuple.Pair;

public class EmployeeSchedulingConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                // Hard constraints
                shiftsPattern(constraintFactory),
                // Medium constraints
                atLeastOneShiftAndSkillPerSetting(constraintFactory),
                // Soft constraints
                minimumEmployeesPerDayPerShiftPerSkill(constraintFactory)
        };
    }

    Constraint shiftsPattern(ConstraintFactory constraintFactory){
        return constraintFactory.forEach(ShiftAssignment.class)
                .groupBy(ShiftAssignment::getEmployee, toList())
                .filter((employee, assignments) -> {
                    assignments.sort(Comparator.comparing(ShiftAssignment::getDate));

                    return !isValidPattern(assignments);
                        })
                .penalize(HardMediumSoftScore.ONE_HARD)
                .asConstraint("Shifts Main Pattern");
    }

    private static int nextIndex(int index){
        return index==20 ? 0 : index + 1;
    }

    private static boolean check(List<ShiftAssignment> assignments, int rotationIndex){
        ShiftType[] shiftsPattern = new ShiftType[] {
                ShiftType.M, ShiftType.M, ShiftType.M, ShiftType.M, ShiftType.M,
                ShiftType.R, ShiftType.R,
                ShiftType.N, ShiftType.N, ShiftType.N, ShiftType.N, ShiftType.N,
                ShiftType.R, ShiftType.R,
                ShiftType.S, ShiftType.S, ShiftType.S, ShiftType.S, ShiftType.S,
                ShiftType.R, ShiftType.R,
        };

        int j = rotationIndex; //7
        for(int i=0; i<assignments.size(); i++){
            if(assignments.get(i).getShiftType() != shiftsPattern[j])
                return false;
            j = nextIndex(j);
        }
        return true;
    }

    private static boolean isValidPattern(List<ShiftAssignment> assignments){

        //indiceRotation != 6 and indiceRotation != 13 and indiceRotation != 20
        int rotationIndex = 0;
        int consecutiveShifts = 0;
        boolean isBeforeHitRepos = true;
        ShiftType previousShift = assignments.size() > 0 ?
                assignments.get(0).getShiftType() : ShiftType.N; //initially in the first one

        //Initial Check
        for(int i=0; i<5 && i<assignments.size(); i++){
            ShiftAssignment assignment = assignments.get(i);

            if(assignment.getShiftType() == ShiftType.R)    isBeforeHitRepos = false;
            if(isBeforeHitRepos){
                if (assignment.getShiftType() != previousShift){
                    return false; //different shifts
                }
                else{
                    consecutiveShifts++;
                    if(consecutiveShifts > 5)   return false; //Exceeded max consecutive shifts
                }

                previousShift = assignment.getShiftType();
            }
            else{
                break;
            }

        }

        //Knowing what's the rotationIndex based on the initial check
        if(previousShift == ShiftType.M){
            rotationIndex = 0 + (5 - consecutiveShifts);
        }
        else if (previousShift == ShiftType.N){
            rotationIndex = 7 + (5 - consecutiveShifts);
        }
        else if (previousShift == ShiftType.S){
            rotationIndex = 14 + (5 - consecutiveShifts);
        }

        //final Check
        if(previousShift == ShiftType.R){

            if(!check(assignments, 5)
                    && !check(assignments, 12)
                    && !check(assignments, 19))
                return false;
        }
        else{
            if(!check(assignments, rotationIndex))
                return false;
        }

        return true;
    }

    Constraint minimumEmployeesPerDayPerShiftPerSkill(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Setting.class)
                .join(ShiftAssignment.class,
                        equal(Setting::getDay, assignment -> assignment.getDate().getDayOfWeek()),
                        equal(Setting::getShift, ShiftAssignment::getShiftType))

                .filter((setting, assignment) ->
                        assignment.getEmployee().getSkills().contains(setting.getSkill()))

                .groupBy((setting, assignment) -> setting,
                        (setting, assignment) -> assignment.getDate(),
                        countBi())

                .filter((setting, date, count) -> count < setting.getMinEmployees())

                .penalize(HardMediumSoftScore.ONE_SOFT,
                        (setting, date, count) -> setting.getMinEmployees() - count)
                .asConstraint("Minimum Employees Per Day Per Shift Per Skill");
    }



    Constraint atLeastOneShiftAndSkillPerSetting(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Setting.class)
                .join(ShiftAssignment.class,
                        equal(Setting::getDay, assignment -> assignment.getDate().getDayOfWeek())
                        )

                .groupBy((setting, assignment) -> setting,
                        (setting, assignment) -> assignment.getDate(),
                        toSet((setting, assignment) ->  assignment.getEmployee().getSkills().stream().map(skill ->
                                Pair.of(assignment.getShiftType(), skill)

                        ).collect(Collectors.toSet()))
                )

                .filter((setting, date, set) -> {
                    for(Set<Pair<ShiftType, String>> innerSet : set){
                        if(innerSet.contains(Pair.of(setting.getShift(), setting.getSkill())))
                            return false;
                    }
                    return true;

                })

                .penalize(HardMediumSoftScore.ONE_MEDIUM)
                .asConstraint("At Least One Shift And Skill Per Setting");
    }


    /*Constraint atLeastOneMSNPerDay(ConstraintFactory constraintFactory){
        return constraintFactory.forEach(ShiftAssignment.class)
                .groupBy(ShiftAssignment::getDate, toSet(ShiftAssignment::getShiftType) )
                .filter((date, set) -> {
                    if(!set.contains(ShiftType.M)
                            || !set.contains(ShiftType.S)
                            || !set.contains(ShiftType.N))
                        return true;

                    return false;
                })
                .penalize(HardMediumSoftScore.ONE_MEDIUM, (date, set) -> {
                    int count = 0;
                    if(!set.contains(ShiftType.M))   count++;
                    if(!set.contains(ShiftType.S))   count++;
                    if(!set.contains(ShiftType.N))   count++;
                    return count;
                })
                .asConstraint("At Least One M, One S, One N Per Day ");
    }*/


}
