package lesson16_GoodCode.task1.departament.dao;

import lesson16_GoodCode.task1.departament.Departament;
import lesson16_GoodCode.task1.model.departament.DateRange;
import lesson16_GoodCode.task1.model.departament.jdbc.Employee;
import lesson16_GoodCode.task1.model.departament.Report;
import lesson16_GoodCode.task1.model.departament.jdbc.ReportJdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JdbcDepartament implements Departament<Employee> {
    private final Connection connection;
    private final String QUERY = "select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary from employee emp left join" +
            "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
            " sp.date >= ? and sp.date <= ? group by emp.id, emp.name";

    public JdbcDepartament(Connection connection) {
        this.connection = connection;
    }

    /**
     * Получение экземпляра PrepareStatement
     */
    private PreparedStatement getPrepareStatement() throws SQLException {
        return connection.prepareStatement(QUERY);
    }


    /**
     * Подготовить запрос к БД
     */
    private void setStatementParameters(PreparedStatement preparedStatement, String departmentId, DateRange dateRange) throws SQLException {
        preparedStatement.setString(0, departmentId);
        preparedStatement.setDate(1, new java.sql.Date(dateRange.getDateFrom().toEpochDay()));
        preparedStatement.setDate(2, new java.sql.Date(dateRange.getDateTo().toEpochDay()));

    }

    /**
     * Подготовить подготовленое заявление
     *
     * @param departmentId ID департамента
     * @param dateRange    Временной период
     * @return Подготовленный запрос
     */
    private PreparedStatement getPreparedStatement(String departmentId, DateRange dateRange) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPrepareStatement();
            setStatementParameters(preparedStatement, departmentId, dateRange);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Сгененерировать отчет
     * @param employees список сотрудников с зарплатами
     * @return отчет
     */
    private Report generateReport(List<Employee> employees){
        double total = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();

        return new ReportJdbc(employees, total);
    }


    /**
     * Закрытие PrepareStatement
     */
    public void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Получить отчет по выплате заработной платы департаменту
     *
     * @param departmentId ID департамента
     * @param dateRange    Временной период
     * @return Отчет по выплатам
     */
    @Override
    public Report getReport(String departmentId, DateRange dateRange) {
        final List<Employee> employees = new LinkedList<>();
        final PreparedStatement preparedStatement = getPreparedStatement(departmentId, dateRange);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee user = new Employee(resultSet.getString("emp_name"), resultSet.getDouble("salary"));
                employees.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return generateReport(employees);
    }

}
