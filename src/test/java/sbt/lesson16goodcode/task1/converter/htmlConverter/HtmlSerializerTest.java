package sbt.lesson16goodcode.task1.converter.htmlConverter;

import sbt.lesson16goodcode.task1.model.converter.View;
import sbt.lesson16goodcode.task1.model.departament.Report;
import sbt.lesson16goodcode.task1.model.departament.jdbc.Employee;
import sbt.lesson16goodcode.task1.converter.Serializer;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HtmlSerializerTest {

    Report report;
    Employee employee;
    @Before
    public void setUp() throws Exception {
        report = mock(Report.class);
        employee = mock(Employee.class);
    }

    @Test
    public void buildTest() throws Exception {
        final String HTML_REPORT = "<html><body><table><tr><td>Employee</td><td>Salary</td></tr>"
                +"<tr><td>Вася</td><td>20.0</td></tr><tr><td>Петя</td><td>33.3</td>"
                +"</tr><tr><td>Total</td><td>53.3</td></tr></table></body></html>";
        List<Employee> employees = Arrays.asList(employee,employee);
        when(report.getEmployees()).thenReturn(employees);
        when(report.getTotal()).thenReturn(53.3);
        when(employee.getName()).thenReturn("Вася","Петя");
        when(employee.getSalary()).thenReturn(20.0,33.3);

        Serializer serializer = new HtmlSerializer();
        View view = serializer.build(report);
        assertEquals("failed buildTest",HTML_REPORT,view.toString());
    }

    @Test
    public void buildEmptyTest() throws Exception {
        final String HTML_REPORT = "<html><body><table><tr><td>Employee</td><td>Salary</td></tr><tr>"
                +"<td>Total</td><td>0.0</td></tr></table></body></html>";
        List<Employee> employees = Arrays.asList();
        when(report.getEmployees()).thenReturn(employees);
        when(report.getTotal()).thenReturn(0.0);

        Serializer serializer = new HtmlSerializer();
        View view = serializer.build(report);
        assertEquals("failed buildEmptyTest",HTML_REPORT,view.toString());
    }
}