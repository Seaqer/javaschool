package sbt.lesson16goodcode.task1.converter.htmlConverter;

import sbt.lesson16goodcode.task1.converter.Serializer;
import sbt.lesson16goodcode.task1.model.converter.html.Content;
import sbt.lesson16goodcode.task1.model.converter.html.Tag;
import sbt.lesson16goodcode.task1.model.departament.EmployeeI;
import sbt.lesson16goodcode.task1.model.departament.Report;
import sbt.lesson16goodcode.task1.model.converter.View;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class HtmlSerializer implements Serializer {

    /**
     * Собрать отчет в формате HTML
     */
    @Override
    public View build(Report report) {
        Objects.requireNonNull(report);

        View htmlBody = new Tag("<html><body>", "</body></html>");
        htmlBody.addItem(getTable(report));
        return htmlBody;
    }

    /**
     * Сгененрировать таблицу из отчета
     *
     * @param report отчет
     * @return представление таблицы
     */
    private View getTable(Report report) {
        final List<EmployeeI<?, ?>> employees = report.getEmployees();
        final View table = new Tag("<table>", "</table>");

        table.addItem(getRow("Employee", "Salary"));
        employees.forEach(new Consumer<EmployeeI<?, ?>>() {
            @Override
            public void accept(EmployeeI<?, ?> e) {
                table.addItem(HtmlSerializer.this.getRow(e.getName().toString(), e.getSalary().toString()));
            }
        });
        table.addItem(getRow("Total", report.getTotal().toString()));

        return table;
    }

    /**
     * Получение строки таблицы
     */
    private View getRow(String fist, String second) {
        View row = new Tag("<tr>", "</tr>");
        row.addItem(new Content("<td>", fist, "</td>"));
        row.addItem(new Content("<td>", second, "</td>"));
        return row;
    }
}
