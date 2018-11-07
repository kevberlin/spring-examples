package com.example.basicconfiguration.config;

import com.example.basicconfiguration.app.AchtungBaby;
import com.example.basicconfiguration.app.CDPlayer;
import com.example.basicconfiguration.app.CompactDisc;
import com.example.basicconfiguration.app.LowFiCDPlayer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LowFiConfig {

    @Bean
    public CompactDisc achtungBaby() {
        return new AchtungBaby();
    }

    @Bean
    public CDPlayer cdPlayer(CompactDisc cd) {
        return new LowFiCDPlayer(cd);
    }
}
