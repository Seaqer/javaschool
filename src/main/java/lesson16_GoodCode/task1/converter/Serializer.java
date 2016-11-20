package lesson16_GoodCode.task1.converter;

import lesson16_GoodCode.task1.model.converter.View;
import lesson16_GoodCode.task1.model.departament.Report;


public interface Serializer {

    /**
     * Собрать отчет
     */
    View build(Report report);
}
