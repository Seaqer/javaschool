package lesson16_GoodCode.task1.model.departament;

import java.util.List;


public interface Report<N,S> {
    List<? extends EmployeeI<N,S>> getEmployees() ;
    S getTotal();
}
