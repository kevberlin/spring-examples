package com.example.webmvc.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class NewsUser {

    public NewsUser(String firstname, String lastname, LocalDate birthday, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.username = username;
        this.password = password;
    }

    public NewsUser(Long id, String firstname, String lastname, LocalDate birthday, String username) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_order_seq")
    @SequenceGenerator(name = "gen_order_seq",
            sequenceName = "SEQ_ORDERS_ID", allocationSize = 1)
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

    @Size(min = 4, message = "Muss mind. 4 Zeichen lang sein.")
    private String password;
}
