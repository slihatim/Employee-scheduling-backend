package org.acme.employeescheduling.domain;

import ai.timefold.solver.core.api.score.director.ScoreDirector;
import ai.timefold.solver.core.impl.heuristic.move.Move;
import ai.timefold.solver.core.impl.heuristic.selector.move.factory.MoveListFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeSchedulingMoveFactory implements MoveListFactory<EmployeeSchedule> {

    @Override
    public List<? extends Move<EmployeeSchedule>> createMoveList(EmployeeSchedule solution) {
        List<Move<EmployeeSchedule>> moves = new ArrayList<>();

        // Add ShiftPatternRotateMove
        for (ShiftAssignment shift : solution.getShiftAssignments()) {
            List<ShiftAssignment> employeeAssignments = solution.getShiftAssignments().stream()
                    .filter(a -> a.getEmployee().equals(shift.getEmployee()))
                    .sorted(Comparator.comparing(ShiftAssignment::getDate))
                    .collect(Collectors.toList());

            for (int rotationDays = 1; rotationDays < employeeAssignments.size(); rotationDays++) {
                moves.add(new ShiftPatternRotateMove(shift.getEmployee(), rotationDays, employeeAssignments));
            }
        }

        // You can add more custom moves here if needed

        return moves;
    }
}
