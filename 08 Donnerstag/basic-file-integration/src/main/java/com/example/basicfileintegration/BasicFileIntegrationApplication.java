package com.example.basicfileintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@SpringBootApplication
public class BasicFileIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicFileIntegrationApplication.class, args);
	}

	@Bean
	public IntegrationFlow fileWriterFlow() {
		return IntegrationFlows
				.from(textInChannel())
				.<String, String>transform(t -> t.toUpperCase())
				.handle(Files
						.outboundAdapter(
								new File("/tmp/integration/files"))
						.fileExistsMode(FileExistsMode.APPEND)
						.appendNewLine(true))
				.get();
	}

	@Bean
	public MessageChannel textInChannel() {
		return new DirectChannel();
	}
}
