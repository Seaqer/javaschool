package sbt.lesson16goodcode.task1;

import sbt.lesson16goodcode.task1.converter.Serializer;
import sbt.lesson16goodcode.task1.departament.Departament;
import sbt.lesson16goodcode.task1.factory.ReportNotifierFactory;
import sbt.lesson16goodcode.task1.factory.htmlReportNotifier.HtmlReportNotifierFactory;
import sbt.lesson16goodcode.task1.model.departament.Report;
import sbt.lesson16goodcode.task1.model.notifiers.NotifyDetails;
import sbt.lesson16goodcode.task1.model.converter.View;
import sbt.lesson16goodcode.task1.model.departament.DateRange;
import sbt.lesson16goodcode.task1.model.notifiers.mail.PostDetails;
import sbt.lesson16goodcode.task1.notifiers.Notifier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import javax.mail.MessagingException;
import java.sql.Connection;
import java.time.LocalDate;

public class SalaryHtmlReportNotifier {
    final static Log LOGGER = LogFactory.getLog(SalaryHtmlReportNotifier.class);
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
            LOGGER.info(e);
        }
    }
}