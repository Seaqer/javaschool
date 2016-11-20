package lesson16_GoodCode.task1.factory.htmlReportNotifier;

import lesson16_GoodCode.task1.converter.Serializer;
import lesson16_GoodCode.task1.converter.htmlConverter.HtmlSerializer;
import lesson16_GoodCode.task1.departament.Departament;
import lesson16_GoodCode.task1.departament.dao.JdbcDepartament;
import lesson16_GoodCode.task1.factory.ReportNotifierFactory;
import lesson16_GoodCode.task1.notifiers.Notifier;
import lesson16_GoodCode.task1.notifiers.email.EmailNotifier;

import java.sql.Connection;


public class HtmlReportNotifierFactory implements ReportNotifierFactory {
    private Departament departament;
    private Serializer serializer;
    private Notifier notifier;

    public HtmlReportNotifierFactory(Connection connection, String host) {
        departament = new JdbcDepartament(connection);
        serializer = new HtmlSerializer();
        notifier = new EmailNotifier(host);

    }

    public Departament createDepartament() {
        return departament;
    }

    @Override
    public Serializer createSerializer() {
        return serializer;
    }

    @Override
    public Notifier createNotifiers() {
        return notifier;
    }
}
