package lesson16_GoodCode.task1.controller;

import lesson16_GoodCode.task1.model.PostDetails;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public class MailSender {
    private final JavaMailSenderImpl mailSender;

    public MailSender(String host) {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
    }

    /**
     * Отправить сообщение
     * @param postDetails детали сообщения
     */
    public void sendMessage(PostDetails postDetails) throws MessagingException {
        MimeMessage message = constructMessage(postDetails);
        mailSender.send(message);
    }

    /**
     * Получить сообщение
     * @param postDetails детали сообщения
     */
    private MimeMessage constructMessage(PostDetails postDetails) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(postDetails.getRECIPIENTS());
        helper.setText(postDetails.getTEXT(), postDetails.isHMTL_FORMAT());
        helper.setSubject(postDetails.getSUBJECT());
        return message;
    }
}
