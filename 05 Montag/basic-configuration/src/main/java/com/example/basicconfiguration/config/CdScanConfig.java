package com.example.basicconfiguration.config;

import com.example.basicconfiguration.app.CDMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CDMarker.class)
public class CdScanConfig {
}