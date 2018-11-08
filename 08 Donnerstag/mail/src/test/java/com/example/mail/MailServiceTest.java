package com.example.mail;

import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    private GreenMail greenMail;

    @Autowired
    private MailService mailService;

    @Before
    public void setup() {
        ServerSetup config = ServerSetupTest.SMTP;
        config.setServerStartupTimeout(10000);
        greenMail = new GreenMail(config);
        greenMail.start();
        GreenMailUser greenMailUser =
                greenMail.setUser("sender@test.org", "password");
        GreenMailUser greenMailUser2 =
                greenMail.setUser("receiver@test.org", "password");
    }

    @Test
    public void testMailService() throws IOException, MessagingException {
        mailService.sendMail();
        greenMail.waitForIncomingEmail(500, 1);
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        MimeMessage mimeMessage = messages[0];
        assertThat(mimeMessage.getContent().toString().trim())
                .isEqualTo("Text");
        assertThat(mimeMessage.getSubject()).isEqualTo("Subject");
        
    }
}