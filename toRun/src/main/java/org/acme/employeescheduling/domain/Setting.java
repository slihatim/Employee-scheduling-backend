package org.acme.employeescheduling.domain;

import ai.timefold.solver.core.api.domain.solution.ProblemFactProperty;

import java.time.DayOfWeek;
import java.util.Objects;

public class Setting {
    private String id;
    private DayOfWeek day;
    private ShiftType shift;
    private String skill;
    private int minEmployees;

    public Setting(){

    }

    public Setting(String id, DayOfWeek day, ShiftType shift, String skill, int minEmployees) {
        this.id = id;
        this.day = day;
        this.shift = shift;
        this.skill = skill;
        this.minEmployees = minEmployees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public ShiftType getShift() {
        return shift;
    }

    public void setShift(ShiftType shift) {
        this.shift = shift;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getMinEmployees() {
        return minEmployees;
    }

    public void setMinEmployees(int numEmployees) {
        this.minEmployees = numEmployees;
    }

    @Override
    public String toString(){
        return day.name() + shift.name() + skill + minEmployees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Setting setting)) {
            return false;
        }
        return Objects.equals(getId(), setting.getId());
    }

    @Override
    public int hashCode(){
        return getId().hashCode();
    }
}
