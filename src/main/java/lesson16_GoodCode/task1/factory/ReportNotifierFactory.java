package lesson16_GoodCode.task1.factory;

import lesson16_GoodCode.task1.converter.Serializer;
import lesson16_GoodCode.task1.departament.Departament;
import lesson16_GoodCode.task1.notifiers.Notifier;

/**
 * Created by Артём on 20.11.2016.
 */
public interface ReportNotifierFactory {
    Departament createDepartament();
    Serializer createSerializer();
    Notifier createNotifiers();
}
