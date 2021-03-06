package com.example.webmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@SpringBootApplication
@EnableScheduling
//@EnableJpaRepositories(bootstrapMode = BootstrapMode.DEFERRED)
public class WebmvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebmvcApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplateBuilder builder = new RestTemplateBuilder();
		return builder.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
