package com.example.basicconfiguration.config;

import com.example.basicconfiguration.app.AchtungBaby;
import com.example.basicconfiguration.app.CompactDisc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompactDiscConfig {
    @Bean
    public CompactDisc achtungBaby() {
        return new AchtungBaby();
    }
}
