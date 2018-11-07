package com.example.databaseexamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;

@SpringBootApplication
@EnableJpaRepositories(bootstrapMode = BootstrapMode.DEFERRED) // parallel thread startup
public class DatabaseExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseExamplesApplication.class, args);
	}
}
