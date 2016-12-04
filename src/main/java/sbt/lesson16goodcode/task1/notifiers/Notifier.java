package sbt.lesson16goodcode.task1.notifiers;

import sbt.lesson16goodcode.task1.model.notifiers.NotifyDetails;

import javax.mail.MessagingException;

public interface Notifier {
    void send(NotifyDetails notifyDetails) throws MessagingException;
}
