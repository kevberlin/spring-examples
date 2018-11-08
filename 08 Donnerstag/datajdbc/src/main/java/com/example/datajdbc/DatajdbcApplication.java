package com.example.datajdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;

@SpringBootApplication
@EnableJdbcRepositories
@Import(JdbcConfiguration.class)
public class DatajdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatajdbcApplication.class, args);
	}
}
