package lesson16_GoodCode.task1.departament;

import lesson16_GoodCode.task1.model.departament.DateRange;
import lesson16_GoodCode.task1.model.departament.Report;

import java.util.List;

public interface Departament<E> {
    Report getReport(String departmentId, DateRange dateRange);
}
