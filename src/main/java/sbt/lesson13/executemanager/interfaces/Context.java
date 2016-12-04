package sbt.lesson13.executemanager.interfaces;

/**
 * Created by Артём on 29.10.2016.
 */
public interface Context {

    /**
     * Возвращает количество тасков, которые на текущий момент успешно выполнились.
     * @return количество тасков
     */
    int getCompletedTaskCount();

    /**
     * Возвращает количество тасков, при выполнении которых произошел exception.
     * @return количество тасков
     */
    int getFailedTaskCount();

    /**
     * Возвращает количество тасков, которые не были выполены из-за отмены.
     * @return количество тасков
     */
    int getInterruptedTaskCount();

    /**
     * Отменяет выполнения тасков, которые еще не начали выполняться.
     */
    void interrupt();

    /**
     * Возвращает все ли таски были завершены или прерваны
     * @return true/false
     */
    boolean isFinished();
}
