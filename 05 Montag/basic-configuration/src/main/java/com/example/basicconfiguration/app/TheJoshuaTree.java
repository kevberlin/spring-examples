package com.example.basicconfiguration.app;

import org.springframework.stereotype.Component;

@Component
public class TheJoshuaTree implements CompactDisc {
    @Override
    public String getContents() {
        return "The Joshua Tree";
    }
}