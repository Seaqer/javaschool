package lesson16_GoodCode.task1.notifiers;

import lesson16_GoodCode.task1.model.notifiers.NotifyDetails;

import javax.mail.MessagingException;

public interface Notifier {
    void send(NotifyDetails notifyDetails) throws MessagingException;
}
