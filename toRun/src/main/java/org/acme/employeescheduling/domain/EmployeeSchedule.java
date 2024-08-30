package org.acme.employeescheduling.domain;

import java.util.List;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolverStatus;

@PlanningSolution
public class EmployeeSchedule {
    @PlanningEntityCollectionProperty
    private List<ShiftAssignment> shiftAssignments;

    @ProblemFactCollectionProperty
    private List<Setting> settings;

    @ValueRangeProvider(id = "shiftRange")
    private List<ShiftType> shiftTypes;

    @PlanningScore
    private HardMediumSoftScore score;

    private SolverStatus solverStatus;

    // No-arg constructor required for Timefold
    public EmployeeSchedule() {}

    public EmployeeSchedule(List<ShiftAssignment> shiftAssignments, List<Setting> settings, List<ShiftType> shiftTypes) {
        this.shiftAssignments = shiftAssignments;
        this.settings = settings;
        this.shiftTypes = shiftTypes;
    }

    public EmployeeSchedule(HardMediumSoftScore score, SolverStatus solverStatus) {
        this.score = score;
        this.solverStatus = solverStatus;
    }

    public List<ShiftAssignment> getShiftAssignments() {
        return shiftAssignments;
    }

    public void setShiftAssignments(List<ShiftAssignment> shiftAssignments) {
        this.shiftAssignments = shiftAssignments;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

    public List<ShiftType> getShiftTypes() {
        return shiftTypes;
    }

    public void setShiftTypes(List<ShiftType> shiftTypes) {
        this.shiftTypes = shiftTypes;
    }

    public HardMediumSoftScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftScore score) {
        this.score = score;
    }

    public SolverStatus getSolverStatus() {
        return solverStatus;
    }

    public void setSolverStatus(SolverStatus solverStatus) {
        this.solverStatus = solverStatus;
    }
}
