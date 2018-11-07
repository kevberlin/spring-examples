package com.example.advancedconfiguration;

import com.example.advancedconfiguration.aspects.CDCounterAspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AdvancedConfigurationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvancedConfigurationApplication.class, args);
	}

	@Bean
	@Profile("lowfi")
	@Primary
	public CDPlayer lowFi(CompactDisc cd) {
		return new LowFiCDPlayer(cd);
	}

	@Bean
	@Profile("lowfi")
	@Qualifier("player2")
	public CDPlayer lowFi2(@Qualifier("achtungbaby") CompactDisc cd) {
		return new LowFiCDPlayer(cd);
	}

	@Bean
	@Profile("hifi")
	public CDPlayer hiFi(CompactDisc cd) {
		return new HiFiCDPlayer(cd);
	}

	@Bean
	public CDCounterAspect counterAspect() {
		return new CDCounterAspect();
	}
}
