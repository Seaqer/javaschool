package lesson16_GoodCode.task1.model.departament;

import java.time.LocalDate;



/**
 * Временной интервал
 */
public class DateRange {
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    /**
     * Получить дату начала
     */
    public LocalDate getDateFrom() {
        return dateFrom;
    }

    /**
     * Получить дату окончания
     */
    public LocalDate getDateTo() {
        return dateTo;
    }

    /**
     * Временной интервал
     * @param dateFrom Дата начала
     * @param dateTo Дата окончания
     */
    public DateRange(LocalDate dateFrom, LocalDate dateTo) {

        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
