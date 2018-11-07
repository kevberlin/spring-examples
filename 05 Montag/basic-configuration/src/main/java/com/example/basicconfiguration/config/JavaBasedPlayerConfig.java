package com.example.basicconfiguration.config;

import com.example.basicconfiguration.app.CDPlayer;
import com.example.basicconfiguration.app.CompactDisc;
import com.example.basicconfiguration.app.LowFiCDPlayer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JavaBasedCompactDiscConfig.class)
public class JavaBasedPlayerConfig {

    @Bean
    public CDPlayer player(CompactDisc cd) {
        return new LowFiCDPlayer(cd);
    }
}