package sbt.lesson16goodcode.task1.model.departament;

import java.util.List;


public interface Report<N,S> {
    List<? extends EmployeeI<N,S>> getEmployees() ;
    S getTotal();
}
