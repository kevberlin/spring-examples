package com.example.basicconfiguration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:cdContext.xml")
public class XmlBasedConfig {
}
