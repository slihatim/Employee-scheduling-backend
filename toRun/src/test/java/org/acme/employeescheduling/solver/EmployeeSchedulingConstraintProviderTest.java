package org.acme.employeescheduling.solver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;

import ai.timefold.solver.test.api.score.stream.ConstraintVerifier;

import org.acme.employeescheduling.domain.Employee;
import org.acme.employeescheduling.domain.EmployeeSchedule;
import org.acme.employeescheduling.domain.ShiftAssignment;
import org.acme.employeescheduling.domain.ShiftType;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class EmployeeSchedulingConstraintProviderTest {

    private static final LocalDate startDate = LocalDate.of(2024, 7, 1);

    /*private static Employee employee0 = new Employee("worker 0");
    private static Employee employee1 = new Employee("worker 1");
    private static Employee employee2 = new Employee("worker 2");
    private static Employee employee3 = new Employee("worker 3");
    private static Employee employee4 = new Employee("worker 4");
    private static Employee employee5 = new Employee("worker 5");
    private static Employee employee6 = new Employee("worker 6");
    private static Employee employee7 = new Employee("worker 7");
    private static Employee employee8 = new Employee("worker 8");
    private static Employee employee9 = new Employee("worker 9");
    private static Employee employee10 = new Employee("worker 10");
    private static Employee employee11 = new Employee("worker 11");
    private static Employee employee12 = new Employee("worker 12");
    private static Employee employee13 = new Employee("worker 13");
    private static Employee employee14 = new Employee("worker 14");
    private static Employee employee15 = new Employee("worker 15");

    private static List<ShiftAssignment> shiftAssignments = new ArrayList<>();

    private static String[] shiftTypes = {*/
//    /*0*/     "N", "N", "N", "N", "N", "R", "R", "S", "S", "S", "S", "S", "R", "R", "M", "M", "M", "M", "M", "R", "R",
//    /*1*/     "N", "N", "N", "N", "N", "R", "R", "S", "S", "S", "S", "S", "R", "R", "M", "M", "M", "M", "M", "R", "R",
//    /*2*/     "S", "S", "S", "S", "S", "R", "R", "M", "M", "M", "M", "M", "R", "R", "N", "N", "N", "N", "N", "R", "R",
//    /*3*/     "R", "R", "N", "N", "N", "N", "N", "R", "R", "S", "S", "S", "S", "S", "R", "R", "M", "M", "M", "M", "M",
//    /*4*/     "M", "M", "M", "M", "M", "R", "R", "N", "N", "N", "N", "N", "R", "R", "S", "S", "S", "S", "S", "R", "R",
//    /*5*/     "M", "M", "M", "M", "M", "R", "R", "N", "N", "N", "N", "N", "R", "R", "S", "S", "S", "S", "S", "R", "R",
//    /*6*/     "M", "M", "M", "R", "R", "N", "N", "N", "N", "N", "R", "R", "S", "S", "S", "S", "S", "R", "R", "M", "M",
//    /*7*/     "M", "M", "M", "M", "M", "R", "R", "N", "N", "N", "N", "N", "R", "R", "S", "S", "S", "S", "S", "R", "R",
//    /*8*/     "S", "R", "R", "M", "M", "M", "M", "M", "R", "R", "N", "N", "N", "N", "N", "R", "R", "S", "S", "S", "S",
//    /*9*/     "S", "S", "S", "R", "R", "M", "M", "M", "M", "M", "R", "R", "N", "N", "N", "N", "N", "R", "R", "S", "S",
//    /*10*/    "R", "R", "S", "S", "S", "S", "S", "R", "R", "M", "M", "M", "M", "M", "R", "R", "N", "N", "N", "N", "N",
//    /*11*/    "N", "N", "R", "R", "S", "S", "S", "S", "S", "R", "R", "M", "M", "M", "M", "M", "R", "R", "N", "N", "N",
//    /*12*/    "R", "R", "S", "S", "S", "S", "S", "R", "R", "M", "M", "M", "M", "M", "R", "R", "N", "N", "N", "N", "N",
//    /*13*/    "R", "R", "S", "S", "S", "S", "S", "R", "R", "M", "M", "M", "M", "M", "R", "R", "N", "N", "N", "N", "N",
//    /*14*/    "M", "M", "M", "M", "M", "R", "R", "N", "N", "N", "N", "N", "R", "R", "S", "S", "S", "S", "S", "R", "R",
//    /*15*/    "S", "S", "S", "R", "R", "M", "M", "M", "M", "M", "R", "R", "N", "N", "N", "N", "N", "R", "R", "S", "S"
    /*};

    private static Employee getEmployee(int i){
        int num = i / 21;
        switch (num){
            case 0 : return employee0;
            case 1 : return employee1;
            case 2 : return employee2;
            case 3 : return employee3;
            case 4 : return employee4;
            case 5 : return employee5;
            case 6 : return employee6;
            case 7 : return employee7;
            case 8 : return employee8;
            case 9 : return employee9;
            case 10 : return employee10;
            case 11 : return employee11;
            case 12 : return employee12;
            case 13 : return employee13;
            case 14 : return employee14;
            case 15 : return employee15;
            default: return employee15;
        }
    }

    static {
        for(int i=0; i< shiftTypes.length; i++){
            shiftAssignments.add(new ShiftAssignment(String.valueOf(i+1), getEmployee(i), startDate.plusDays(i%21), ShiftType.valueOf(shiftTypes[i])));
        }
    }
    */



    @Inject
    ConstraintVerifier<EmployeeSchedulingConstraintProvider, EmployeeSchedule> constraintVerifier;

    /*@Test
    void shiftsPattern() {
        constraintVerifier.verifyThat(EmployeeSchedulingConstraintProvider::shiftsPattern)
                .given(shiftAssignments.toArray(new ShiftAssignment[0])
                        )
                .penalizes(0);
    }*/

    /*@Test
    void atLeastOneMSNPerDay() {
        constraintVerifier.verifyThat(EmployeeSchedulingConstraintProvider::atLeastOneMSNPerDay)
                .given(shiftAssignments.toArray(new ShiftAssignment[0]))
                .penalizesBy(0);
    }

    @Test
    void minimumEmployeesPerShift() {
        constraintVerifier.verifyThat(EmployeeSchedulingConstraintProvider::minimumEmployeesPerShift)
                .given(shiftAssignments.toArray(new ShiftAssignment[0]))
                .penalizesBy(18);
    }*/

    /*@Test
    void atLeastOneMSNPerDay() {
        constraintVerifier.verifyThat(EmployeeSchedulingConstraintProvider::atLeastOneMSNPerDay)
                .given(shift1, shift8, shift15,

                        shift22, shift23, shift24,
                        shift25, shift26, shift27,
                        shift28, shift29, shift30,
                        shift31, shift32, shift33,
                        shift34, shift35, shift36,
                        shift37, shift38, shift39,
                        shift40, shift41, shift42,
                        shift43, shift44, shift45,
                        shift46, shift47, shift48,
                        shift49, shift50, shift51,
                        shift52, shift53, shift54
                )
                .penalizes(0);
    }*/
}