package lesson16_GoodCode.task1.notifiers.email;

import lesson16_GoodCode.task1.model.notifiers.NotifyDetails;
import lesson16_GoodCode.task1.model.notifiers.mail.PostDetails;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import lesson16_GoodCode.task1.notifiers.Notifier;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public class EmailNotifier implements Notifier {
    private final JavaMailSenderImpl mailSender;

    public EmailNotifier(String host) {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
    }

    /**
     * Отправить сообщение
     *
     * @param notifyDetails детали сообщения
     */
    public void send(NotifyDetails notifyDetails) throws MessagingException {
        MimeMessage message = constructMessage(notifyDetails);
        mailSender.send(message);
    }

    /**
     * Получить сообщение
     *
     * @param notifyDetails детали сообщения
     */
    private MimeMessage constructMessage(NotifyDetails notifyDetails) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(notifyDetails.getRecipient());
        helper.setText(notifyDetails.getText(), true);
        helper.setSubject(notifyDetails.getSubject());
        return message;
    }
}
