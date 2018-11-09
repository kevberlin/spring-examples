package com.example.reactive;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class HelloControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHello() {
        ResponseEntity<String> entity =
                this.restTemplate.getForEntity("/hello/{who}",
                        String.class, "Walter");
        assertThat(entity.getBody())
                .isEqualToIgnoringCase("Hello Walter!");
    }
}
