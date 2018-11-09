package com.example.reactive;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PersonRestController {

    private ReactivePersonRepository personRepository;

    public PersonRestController(ReactivePersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/person")
    public Flux<Person> findAll() {
        return personRepository.findAll();
    }

    @PostMapping("/person")
    public Mono<Person> save(@RequestBody Mono<Person> personMono) {
        return personMono.flatMap(p -> personRepository.save(p));
    }

    @GetMapping("/person/{id}")
    public Mono<Person> findById(@PathVariable("id") String id) {
        return personRepository.findById(id);
    }

    @PatchMapping("/person/{id}")
    public Mono<Person> patchById(@RequestBody Mono<Person> personMono) {
        return personMono
                .flatMap(p -> {
                    System.out.println(p);
                    return personRepository.save(p);
                });
    }
}
