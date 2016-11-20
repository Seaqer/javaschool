package lesson16_GoodCode.task1.notifiers.email;

import lesson16_GoodCode.task1.model.converter.View;
import lesson16_GoodCode.task1.model.notifiers.mail.PostDetails;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;


public class EmailNotifierTest {
    @Test
    public void send() throws Exception {
        View view = mock(View.class);
        EmailNotifier email = new EmailNotifier("mail.google.com");
        when(view.toString()).thenReturn("<html><body><table><tr><td>Employee</td><td>Salary</td></tr><tr><td>Total</td><td>0.0</td></tr></table></body></html>");
        MimeMessageHelper mockMimeMessageHelper = getMockedMimeMessageHelper();
        email.send(new PostDetails(view, "somebody@gmail.com", "Monthly department salary report"));
        assertActualReportEqualsTo(mockMimeMessageHelper);
    }

    private void assertActualReportEqualsTo(MimeMessageHelper mockMimeMessageHelper) throws MessagingException {
        ArgumentCaptor<String> messageTextArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockMimeMessageHelper).setText(messageTextArgumentCaptor.capture(), anyBoolean());

    }

    private MimeMessageHelper getMockedMimeMessageHelper() throws Exception {
        JavaMailSenderImpl mockMailSender = mock(JavaMailSenderImpl.class);
        MimeMessage mockMimeMessage = mock(MimeMessage.class);
        when(mockMailSender.createMimeMessage()).thenReturn(mockMimeMessage);
        whenNew(JavaMailSenderImpl.class).withNoArguments().thenReturn(mockMailSender);
        MimeMessageHelper mockMimeMessageHelper = mock(MimeMessageHelper.class);
        whenNew(MimeMessageHelper.class).withArguments(any(), eq(true))
                .thenReturn(mockMimeMessageHelper);
        return mockMimeMessageHelper;
    }
}