package lesson16_GoodCode.task1.converter.htmlConverter;

import lesson16_GoodCode.task1.model.converter.View;
import lesson16_GoodCode.task1.model.departament.Report;
import lesson16_GoodCode.task1.model.departament.jdbc.Employee;
import lesson16_GoodCode.task1.model.departament.jdbc.ReportJdbc;
import lesson16_GoodCode.task1.converter.Serializer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Артём on 20.11.2016.
 */
public class HtmlSerializerTest {
    @Test
    public void build() throws Exception {
        List<Employee> e = new ArrayList<>();
        e.add(new Employee("Вася",30.0));
        e.add(new Employee("Пеася",33.0));
        Report report = new ReportJdbc(e,e.stream()
                .mapToDouble(Employee::getSalary)
                .sum());

        Serializer serializer= new HtmlSerializer();
        View view =  serializer.build(report);
        String str = view.toString();

        String str1= str;
        int b=0;

    }

}