package lesson16_GoodCode.task1.model.departament;

/**
 * Created by Артём on 20.11.2016.
 */
public interface EmployeeI<N,S> {
    /**
     * Получить ФИО сотрудника
     */
    N getName();

    /**
     * Получить зарплату сотрудника
     */
    S getSalary();
}
