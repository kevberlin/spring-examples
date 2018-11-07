package com.example.webmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@AllArgsConstructor
public class PartialUser {

    public PartialUser(NewsUser newsUser) {
        this.id = newsUser.getId();
        this.firstname = newsUser.getFirstname();
        this.lastname = newsUser.getLastname();
        this.birthday = newsUser.getBirthday();
        this.username = newsUser.getUsername();
    }

    @Id
    private Long id;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @Past
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Size(min = 4, message = "Muss mind. 4 Zeichen lang sein.")
    private String username;
}
