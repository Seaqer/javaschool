package sbt.lesson16goodcode.task1.factory;

import sbt.lesson16goodcode.task1.converter.Serializer;
import sbt.lesson16goodcode.task1.departament.Departament;
import sbt.lesson16goodcode.task1.notifiers.Notifier;

/**
 * Created by Артём on 20.11.2016.
 */
public interface ReportNotifierFactory {
    Departament createDepartament();
    Serializer createSerializer();
    Notifier createNotifiers();
}
