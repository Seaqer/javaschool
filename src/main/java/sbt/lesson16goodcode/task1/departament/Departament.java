package sbt.lesson16goodcode.task1.departament;

import sbt.lesson16goodcode.task1.model.departament.DateRange;
import sbt.lesson16goodcode.task1.model.departament.Report;

public interface Departament<E> {
    Report getReport(String departmentId, DateRange dateRange);
}
