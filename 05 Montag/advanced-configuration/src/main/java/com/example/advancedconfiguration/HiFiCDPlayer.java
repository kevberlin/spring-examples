package com.example.advancedconfiguration;

import org.springframework.beans.factory.annotation.Autowired;

public class HiFiCDPlayer implements CDPlayer {

    private CompactDisc cd;

    @Autowired
    public HiFiCDPlayer(CompactDisc cd) {
        this.cd = cd;
    }

    @Override
    public String readCD() {
        return cd.getContents().toUpperCase();
    }
}
