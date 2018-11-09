package com.example.reactive;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class MyReactiveLibrary {

    public Mono<String> withDelay(String value, int delaySeconds) {
        return Mono.just(value)
                .delayElement(Duration.ofSeconds(delaySeconds));
    }
}
