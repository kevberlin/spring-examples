package com.example.basicconfiguration.app;

import org.springframework.stereotype.Component;

@Component
public class ConstrutorWiredCdPlayer implements CDPlayer {

    private CompactDisc cd;

    public ConstrutorWiredCdPlayer(CompactDisc cd) {
        this.cd = cd;
    }

    @Override
    public String readCD() {
        return cd.getContents();
    }
}