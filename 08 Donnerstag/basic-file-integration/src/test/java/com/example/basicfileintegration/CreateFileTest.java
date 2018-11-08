package com.example.basicfileintegration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Receiver;

import static junit.framework.TestCase.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateFileTest {

    @Qualifier("textInChannel")
    @Autowired
    private MessageChannel textChannel;

    @Qualifier("numberChannel")
    @Autowired
    private MessageChannel numberChannel;

    @Qualifier("csvChannel")
    @Autowired
    private MessageChannel csvChannel;

    @Test
    public void sendTextAndExpectFileCreation() {
        boolean send = textChannel.send(new GenericMessage<>("String"));
        assertTrue(send);
    }

    @Test
    public void sendMultipleNumbers() {
        for(int i=0;i<5;i++) {
            numberChannel.send(new GenericMessage<>(i));
        }
    }
    
    @Test
    public void testCsvSplitter() {
        csvChannel.send(new GenericMessage<>("1,2,3,4,5,6,7,8,9"));
    }
}
