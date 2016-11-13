package lesson16_GoodCode.task1.controller;

import lesson16_GoodCode.task1.model.DateRange;
import lesson16_GoodCode.task1.model.PostDetails;

import javax.mail.MessagingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SalaryHtmlReportNotifier {

    private final Connection connection;
    private final SalaryPaymentsReader salaryPaymentsReader;
    private final MailSender mailSender;

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        this.connection = databaseConnection;
        this.salaryPaymentsReader = new SalaryPaymentsReader(connection);
        this.mailSender = new MailSender("mail.google.com");
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, DateRange dateRange, String recipients) {
        try {
            ResultSet results = salaryPaymentsReader.getSalaryPayments(departmentId, dateRange);
            String resultingHtml = HtmlBuilder.convertDatabaseResponse(results);
            PostDetails postDetails = new PostDetails(resultingHtml, recipients, "Monthly department salary report", true);
            mailSender.sendMessage(postDetails);
        } catch (SQLException | MessagingException e) {
            e.printStackTrace();
        }
    }
}