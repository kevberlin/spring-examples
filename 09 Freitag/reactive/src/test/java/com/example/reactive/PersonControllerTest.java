package com.example.reactive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(PersonRestController.class)
public class PersonControllerTest {

    @MockBean
    ReactivePersonRepository repository;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void save() throws Exception {
        Person p = new Person(null,"Karl", "Klammer", 32);
        Mockito.when(repository.save(Mockito.any())).thenReturn(Mono.just(p));
        webClient.post().uri("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(p))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class)
                .isEqualTo(p);
    }

    @Test
    public void findAll() throws Exception {
        Person p = new Person(null, "Karl", "Klammer", 32);
        Mockito.when(repository.findAll()).thenReturn(Flux.just(p));
        webClient.get().uri("/person")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class)
                .hasSize(1)
                .contains(p);
    }
}
