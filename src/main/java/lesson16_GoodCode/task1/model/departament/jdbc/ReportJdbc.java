package lesson16_GoodCode.task1.model.departament.jdbc;

import lesson16_GoodCode.task1.model.departament.EmployeeI;
import lesson16_GoodCode.task1.model.departament.Report;

import java.util.Collections;
import java.util.List;

public class ReportJdbc implements Report<String,Double> {
    private final List<Employee> employees;
    private final double total;

    public List<? extends EmployeeI<String,Double>> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public Double getTotal() {
        return total;
    }

    public ReportJdbc(List<Employee> employees, double total) {
        this.employees = employees;
        this.total = total;
    }
}
