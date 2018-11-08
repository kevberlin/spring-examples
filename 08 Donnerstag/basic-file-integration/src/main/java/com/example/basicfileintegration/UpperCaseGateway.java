package com.example.basicfileintegration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultRequestChannel = "inChannel",
        defaultReplyChannel = "outChannel")
public interface UpperCaseGateway {
    String uppercase(String in);
}
