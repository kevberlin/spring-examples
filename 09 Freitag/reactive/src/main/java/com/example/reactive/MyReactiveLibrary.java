package com.example.reactive;

import reactor.core.publisher.Mono;

import java.time.Duration;

public class MyReactiveLibrary {

    public Mono<String> withDelay(String value, int delaySeconds) {
        return Mono.just(value)
                .delayElement(Duration.ofSeconds(delaySeconds));
    }
}
