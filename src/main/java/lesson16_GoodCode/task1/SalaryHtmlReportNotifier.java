package lesson16_GoodCode.task1;

import lesson16_GoodCode.task1.converter.Serializer;
import lesson16_GoodCode.task1.departament.Departament;
import lesson16_GoodCode.task1.factory.ReportNotifierFactory;
import lesson16_GoodCode.task1.factory.htmlReportNotifier.HtmlReportNotifierFactory;
import lesson16_GoodCode.task1.model.departament.Report;
import lesson16_GoodCode.task1.model.notifiers.NotifyDetails;
import lesson16_GoodCode.task1.model.converter.View;
import lesson16_GoodCode.task1.model.departament.DateRange;
import lesson16_GoodCode.task1.model.notifiers.mail.PostDetails;
import lesson16_GoodCode.task1.notifiers.Notifier;


import javax.mail.MessagingException;
import java.sql.Connection;
import java.time.LocalDate;

public class SalaryHtmlReportNotifier {
    private final Departament departament;
    private final Serializer serializer;
    private final Notifier mailSender;

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        ReportNotifierFactory factoryReport = new HtmlReportNotifierFactory(databaseConnection, "mail.google.com");
        this.departament = factoryReport.createDepartament();
        this.serializer = factoryReport.createSerializer();
        this.mailSender = factoryReport.createNotifiers();
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {
        try {
            Report report = departament.getReport(departmentId, new DateRange(dateFrom, dateTo));
            View view = serializer.build(report);
            NotifyDetails notifyDetails = new PostDetails(view, recipients, "Monthly department salary report");
            mailSender.send(notifyDetails);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}