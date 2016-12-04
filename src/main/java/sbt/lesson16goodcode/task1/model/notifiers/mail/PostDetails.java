package sbt.lesson16goodcode.task1.model.notifiers.mail;

import sbt.lesson16goodcode.task1.model.notifiers.NotifyDetails;
import sbt.lesson16goodcode.task1.model.converter.View;

/*
 * Атрибуты сообщения
 */
public class PostDetails implements NotifyDetails{
    private final View VIEW;
    private final String RECIPIENTS;
    private final String SUBJECT;

    public PostDetails(View view, String recipient, String subject) {
        this.VIEW = view;
        this.RECIPIENTS = recipient;
        this.SUBJECT = subject;
    }

    @Override
    public String getText() {
        return VIEW.toString();
    }

    @Override
    public String getRecipient() {
        return RECIPIENTS;
    }

    @Override
    public String getSubject() {
        return SUBJECT;
    }
}
