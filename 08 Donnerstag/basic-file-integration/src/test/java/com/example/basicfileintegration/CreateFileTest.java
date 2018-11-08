package com.example.basicfileintegration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateFileTest {

    @Qualifier("textInChannel")
    @Autowired
    private MessageChannel textChannel;

    @Test
    public void sendTextAndExpectFileCreation() {
        boolean send = textChannel.send(new GenericMessage<>("String"));
        assertTrue(send);
    }
}
