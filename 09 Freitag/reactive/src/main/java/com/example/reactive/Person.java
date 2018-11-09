package com.example.reactive;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

    @Id
    private String id;

    private String firstname;

    private String lastname;

    private int age;
}
