package com.example.basicfileintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class RouterConfiguration {

    @Bean
    @Router(inputChannel = "numberChannel")
    public AbstractMessageRouter evenOddRouter() {
        return new AbstractMessageRouter() {
            @Override
            protected Collection<MessageChannel>
            determineTargetChannels(Message<?> message) {
                Integer number = (Integer) message.getPayload();
                if (number % 2 == 0) {
                    return Collections.singleton(evenChannel());
                }
                return Collections.singleton(oddChannel());
            }
        };
    }

    @Bean
    public MessageChannel evenChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel oddChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel numberChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "evenChannel")
    public MessageHandler sysoutEvenHandler() {
        return message -> {
            System.out.println("Even: " + message.getPayload());
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "oddChannel")
    public MessageHandler sysoutOddHandler() {
        return message -> {
            System.out.println("Odd:  " + message.getPayload());
        };
    }

    @Bean
    public MessageChannel csvChannel() {
        return new DirectChannel();
    }

    @Splitter(inputChannel="csvChannel", outputChannel="numberChannel")
    public List<Integer> lineItemSplitter(String text) {
        String[] strings = text.split(",");
        return Stream.of(strings)
                .map(s -> Integer.valueOf(s))
                .collect(Collectors.toList());
    }

    @Bean
    public IntegrationFlow uppercaseFlow() {
        return IntegrationFlows
                .from(MessageChannels.direct("inChannel"))
                .<String, String>transform(t -> t.toUpperCase())
                .channel("outChannel")
                .get();
    }
}
