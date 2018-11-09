package com.example.reactive;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class HelloFluxTest {

    private static List<String> words = Arrays.asList("the",
            "quick",
            "brown",
            "fox",
            "jumped",
            "over",
            "the",
            "lazy",
            "dog");

    @Test
    public void helloFlux() throws Exception {
        Flux<String> fewWords = Flux.just("Hello", "World");
        Flux<String> manyWords = Flux.fromIterable(words);
        fewWords.subscribe(System.out::println);
        System.out.println();
        manyWords.subscribe(System.out::println);
    }

    @Test
    public void missingLetters1() throws Exception {
        Flux<String> manyLetters = Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .zipWith(Flux.range(1, Integer.MAX_VALUE),
                        (string, count) ->
                                String.format("%2d. %s", count, string));
        manyLetters.subscribe(System.out::println);
    }

    @Test
    public void missingLetters2() throws Exception {
        Flux<String> manyLetters = Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE),
                        (string, count) ->
                                String.format("%2d. %s", count, string));
        manyLetters.subscribe(System.out::println);
    }

    @Test
    public void mono() throws Exception {
        Mono<String> missing = Mono.just("s");
        Flux<String> allLetters = Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .concatWith(missing)
                .distinct()
                .sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE),
                        (string, count) ->
                                String.format("%2d. %s", count, string));
        allLetters.subscribe(System.out::println);
    }

    @Test
    public void virtualTimeTest() throws Exception {
        MyReactiveLibrary library = new MyReactiveLibrary();
        Duration testDuration =
                StepVerifier.withVirtualTime(() ->
                        library.withDelay("foo", 30))
                        .expectSubscription()
                        .thenAwait(Duration.ofSeconds(10))
                        .expectNoEvent(Duration.ofSeconds(10))
                        .thenAwait(Duration.ofSeconds(10))
                        .expectNext("foo")
                        .expectComplete()
                        .verify();
        System.out.println(testDuration.toMillis() + "ms");
    }
}
