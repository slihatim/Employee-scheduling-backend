package org.acme.employeescheduling.domain;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;

public class ShiftAssignmentDifficultyComparator implements Comparator<ShiftAssignment> {

    @Override
    public int compare(ShiftAssignment a, ShiftAssignment b) {
        return new CompareToBuilder()
                .append(getDifficultyWeight(a), getDifficultyWeight(b))
                .append(a.getId(), b.getId())
                .toComparison();
    }

    private int getDifficultyWeight(ShiftAssignment assignment) {
        int weight = 0;
        String name = assignment.getEmployee().getName();

        int number = Integer.parseInt(name.substring(7, name.length()));
        weight = 16 - number;

        return weight;
    }
}