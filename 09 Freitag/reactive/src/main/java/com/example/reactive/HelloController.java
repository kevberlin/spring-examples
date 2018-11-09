package com.example.reactive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    private final MyReactiveLibrary reactiveLibrary;

    public HelloController(MyReactiveLibrary reactiveLibrary) {
        this.reactiveLibrary = reactiveLibrary;
    }

    @GetMapping("hello/{who}")
    public Mono<String> hello(@PathVariable String who) {
        return Mono.just(who)
                .map(w -> "Hello " + w + "!");
    }
}
