package lesson16_GoodCode.task1.controller;

import lesson16_GoodCode.task1.model.DateRange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Считыватель с БД выплат заработной платы департамента
 */
public class SalaryPaymentsReader {

    private final Connection connection;
    private final String QUERY = "select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary from employee emp left join" +
            "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
            " sp.date >= ? and sp.date <= ? group by emp.id, emp.name";

    /**
     * Считыватель с БД выплат заработной платы департамента
     *
     * @param databaseConnection соединение с БД
     */
    public SalaryPaymentsReader(Connection databaseConnection) {
        this.connection = databaseConnection;
    }

    /**
     * Получить список выплат по заработной платы
     *
     * @param departmentId ID департамента
     * @param dateRange    Временной период
     * @return Список выплат
     * @throws SQLException ошибка обращения к БД
     */
    public ResultSet getSalaryPayments(String departmentId, DateRange dateRange) throws SQLException {
        return getStatement(departmentId, dateRange).executeQuery();
    }

    /**
     * Подготовить запрос к БД
     *
     * @param departmentId ID департамента
     * @param dateRange    Временной период
     * @return Подготовленный запрос
     */
    private PreparedStatement getStatement(String departmentId, DateRange dateRange) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(QUERY);

        ps.setString(0, departmentId);
        ps.setDate(1, new java.sql.Date(dateRange.getDateFrom().toEpochDay()));
        ps.setDate(2, new java.sql.Date(dateRange.getDateTo().toEpochDay()));
        return ps;
    }
}
