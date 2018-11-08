package com.example.jms;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String to;
    private String body;
}