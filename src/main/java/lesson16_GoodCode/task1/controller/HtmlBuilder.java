package lesson16_GoodCode.task1.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HtmlBuilder {

    /**
     * Преобразование ответа от БД в HTML
     * @param responseDB ответ базы данных
     * @return преобразованный ответ
     */
    public static String convertDatabaseResponse(ResultSet responseDB) throws SQLException {
        StringBuilder resultingHtml = new StringBuilder();
        double totals = 0;

        resultingHtml.append("<html><body><table>");
        resultingHtml.append("<tr><td>Employee</td><td>Salary</td></tr>");
        while (responseDB.next()) {
            StringBuilder employee = getEmployee(responseDB.getString("emp_name"), responseDB.getDouble("salary"));
            resultingHtml.append(employee);
            totals += responseDB.getDouble("salary");
        }
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        resultingHtml.append("</table></body></html>");
        return resultingHtml.toString();
    }

    /**
     * Обработка строки запроса
     *
     * @param name   имя сотрудника
     * @param salary сумма выплаты сотруднику
     * @return подготовленная строка
     */
    private static StringBuilder getEmployee(String name, double salary) {
        StringBuilder resultingHtml = new StringBuilder();

        resultingHtml.append("<tr>");
        resultingHtml.append("<td>").append(name).append("</td>");
        resultingHtml.append("<td>").append(salary).append("</td>");
        resultingHtml.append("</tr>");
        return resultingHtml;
    }
}
