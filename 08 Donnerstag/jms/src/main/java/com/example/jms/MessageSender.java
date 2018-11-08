package com.example.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageSender implements CommandLineRunner {

    private JmsTemplate jmsTemplate;

    public MessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Sending Message");
        jmsTemplate.convertAndSend("mailbox",
                new Email("info@example.com", "Hello"));
    }
}
