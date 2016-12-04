package sbt.lesson16goodcode.task1.model.notifiers;

public interface NotifyDetails {

    /**
     * Получить текст сообщения
     */
    public String getText();

    /**
     * Получить получателей
     *
     * @return
     */
    public String getRecipient();

    /**
     * Получить тему сообщения
     *
     * @return
     */
    public String getSubject();
}
