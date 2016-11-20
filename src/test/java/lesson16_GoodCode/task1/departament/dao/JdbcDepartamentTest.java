package lesson16_GoodCode.task1.departament.dao;

import lesson16_GoodCode.task1.departament.Departament;
import lesson16_GoodCode.task1.model.departament.DateRange;
import lesson16_GoodCode.task1.model.departament.Report;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcDepartamentTest {
    @Test
    public void getReport() throws Exception {
        final String EXCEPTED = "Report{employees=[Employee{John Doe,100.0}, " +
                "Employee{Jane Dow,50.0}], total=150.0}";
        Connection someFakeConnection = mock(Connection.class);
        ResultSet mockResultSet = getMockedResultSet(someFakeConnection);
        Departament departament = new JdbcDepartament(someFakeConnection);
        Report report = departament.getReport(anyString(),new DateRange(LocalDate.now(),LocalDate.now()));
        Assert.assertEquals("failed getReportJDBC",EXCEPTED,report.toString());
    }

    private ResultSet getMockedResultSet(Connection someFakeConnection) throws SQLException {
        PreparedStatement someFakePreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(someFakePreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(someFakeConnection.prepareStatement(anyString())).thenReturn(someFakePreparedStatement);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("emp_id")).thenReturn(1, 2);
        when(mockResultSet.getString("emp_name")).thenReturn("John Doe", "Jane Dow");
        when(mockResultSet.getDouble("salary")).thenReturn(100.0, 50.0, 100.0, 50.0);
        return mockResultSet;
    }
}