package sbt.lesson16goodcode.task1.converter;

import sbt.lesson16goodcode.task1.model.converter.View;
import sbt.lesson16goodcode.task1.model.departament.Report;


public interface Serializer {

    /**
     * Собрать отчет
     */
    View build(Report report);
}
