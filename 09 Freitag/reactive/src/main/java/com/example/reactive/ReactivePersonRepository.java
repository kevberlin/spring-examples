package com.example.reactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactivePersonRepository
        extends ReactiveCrudRepository<Person, String> {
}
