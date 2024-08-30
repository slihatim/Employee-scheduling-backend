package org.acme.employeescheduling.domain;
import ai.timefold.solver.core.api.score.director.ScoreDirector;
import ai.timefold.solver.core.impl.heuristic.move.AbstractMove;

import java.util.*;
import java.util.stream.Collectors;

public class ShiftPatternRotateMove extends AbstractMove<EmployeeSchedule> {
    private Employee employee;
    private int rotationDays;
    private List<ShiftAssignment> employeeAssignments;
    private List<ShiftAssignment> originalAssignments;

    public ShiftPatternRotateMove(Employee employee, int rotationDays, List<ShiftAssignment> employeeAssignments) {
        this.employee = employee;
        this.rotationDays = rotationDays;
        this.employeeAssignments = employeeAssignments;
        this.originalAssignments = new ArrayList<>();
        for(ShiftAssignment shift : employeeAssignments){
            this.originalAssignments.add(new ShiftAssignment(shift.getId(), shift.getEmployee(), shift.getDate(), shift.getShiftType()));
        }
    }

    @Override
    protected void doMoveOnGenuineVariables(ScoreDirector<EmployeeSchedule> scoreDirector) {
        for (int i = 0; i < employeeAssignments.size(); i++) {
            ShiftAssignment assignment = employeeAssignments.get(i);
            ShiftAssignment rotatedAssignment = originalAssignments.get((i + rotationDays) % employeeAssignments.size());
            scoreDirector.beforeVariableChanged(assignment, "shiftType");
            assignment.setShiftType(rotatedAssignment.getShiftType());
            scoreDirector.afterVariableChanged(assignment, "shiftType");
        }
    }

    @Override
    public boolean isMoveDoable(ScoreDirector<EmployeeSchedule> scoreDirector) {

        int firstRIndex;
        if(employeeAssignments.get(0).getShiftType() == ShiftType.R)    firstRIndex = 0;
        else if(employeeAssignments.get(1).getShiftType() == ShiftType.R)    firstRIndex = 1;
        else if(employeeAssignments.get(2).getShiftType() == ShiftType.R)    firstRIndex = 2;
        else if(employeeAssignments.get(3).getShiftType() == ShiftType.R)    firstRIndex = 3;
        else if(employeeAssignments.get(4).getShiftType() == ShiftType.R)    firstRIndex = 4;
        else    firstRIndex = 5;

        if(rotationDays == firstRIndex + 1 || rotationDays == firstRIndex + 1 + 7 || rotationDays == firstRIndex + 1 + 14)
            return false;

        return employeeAssignments.size() == 21;
    }

    @Override
    public ShiftPatternRotateMove createUndoMove(ScoreDirector<EmployeeSchedule> scoreDirector) {
        return new ShiftPatternRotateMove(employee, 0 , employeeAssignments);
    }

    @Override
    public Collection<? extends Object> getPlanningEntities() {
        return employeeAssignments;
    }

    @Override
    public Collection<? extends Object> getPlanningValues() {
        return employeeAssignments.stream()
                .map(ShiftAssignment::getShiftType)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftPatternRotateMove that = (ShiftPatternRotateMove) o;
        return rotationDays == that.rotationDays &&
                Objects.equals(employee, that.employee) &&
                Objects.equals(employeeAssignments, that.employeeAssignments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, rotationDays, employeeAssignments);
    }

    @Override
    public String toString() {
        return "ShiftPatternRotateMove{" +
                "employee=" + employee +
                ", rotationDays=" + rotationDays +
                ", employeeAssignments=" + employeeAssignments +
                '}';
    }
}
