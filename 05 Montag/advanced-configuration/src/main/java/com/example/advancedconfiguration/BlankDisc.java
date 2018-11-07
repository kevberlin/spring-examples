package com.example.advancedconfiguration;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BlankDisc implements CompactDisc {

    private CompactDiscProperties compactDiscProperties;

    public BlankDisc(CompactDiscProperties compactDiscProperties) {
        this.compactDiscProperties = compactDiscProperties;
    }

    @Override
    public String getContents() {
        return compactDiscProperties.getTitle();
    }
}