package sbt.lesson16goodcode.task1.model.departament.jdbc;

import sbt.lesson16goodcode.task1.model.departament.EmployeeI;

/**
 * Created by Артём on 19.11.2016.
 */

public class Employee implements EmployeeI<String,Double> {
    private final String name;
    private final double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    /**
     * Получить ФИО сотрудника
     */
    public String getName() {
        return name;
    }

    /**
     * Получить зарплату сотрудника
     */
    public Double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{"
                + name  +
                "," + salary +
                '}';
    }
}
