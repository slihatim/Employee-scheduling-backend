package org.acme.employeescheduling.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;

@PlanningEntity(difficultyComparatorClass = ShiftAssignmentDifficultyComparator.class)
public class ShiftAssignment {
    @PlanningId
    private String id;

    private Employee employee;

    private LocalDate date;

    @PlanningVariable(valueRangeProviderRefs = "shiftRange")
    private ShiftType shiftType;

    public ShiftAssignment() {
    }

    public ShiftAssignment(String id, Employee employee, LocalDate date, ShiftType shiftType) {
        this.id = id;
        this.employee = employee;
        this.date = date;
        this.shiftType = shiftType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public void setShiftType(ShiftType shiftType) {
        this.shiftType = shiftType;
    }

    @Override
    public String toString() {
        return employee + " " + date + "-" + shiftType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShiftAssignment shiftAssignment)) {
            return false;
        }
        return Objects.equals(getId(), shiftAssignment.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
