package lesson16_GoodCode.task1.model;

/*
 * Атрибуты сообщения
 */
public class PostDetails {
    private final String TEXT;
    private final String RECIPIENTS;
    private final String SUBJECT;
    private final boolean HMTL_FORMAT;

    /**
     * Проверить cообщение в формате HTML
     */
    public boolean isHMTL_FORMAT() {
        return HMTL_FORMAT;
    }

    /**
     * Получить текст сообщения
     */
    public String getTEXT() {
        return TEXT;
    }

    /**
     * Получить получателей
     *
     * @return
     */
    public String getRECIPIENTS() {
        return RECIPIENTS;
    }

    /**
     * Получить тему сообщения
     *
     * @return
     */
    public String getSUBJECT() {
        return SUBJECT;
    }

    /**
     * Атрибуты сообщения
     * @param text текст
     * @param recipients получатели
     * @param subject тема
     * @param formatHtml формат html
     */
    public PostDetails(String text, String recipients, String subject, boolean formatHtml) {
        this.TEXT = text;
        this.RECIPIENTS = recipients;
        this.SUBJECT = subject;
        this.HMTL_FORMAT = formatHtml;
    }
}
