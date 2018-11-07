package com.example.webmvc.controller.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;

@AllArgsConstructor
public class NewsUserResource extends ResourceSupport {

    @Getter
    private String firstname;
    @Getter
    private String lastname;
    @Getter
    private LocalDate birthday;
    @Getter
    private String username;
}
