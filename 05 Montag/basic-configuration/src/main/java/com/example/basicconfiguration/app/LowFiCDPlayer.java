package com.example.basicconfiguration.app;

public class LowFiCDPlayer implements CDPlayer {

    private CompactDisc cd;

    public LowFiCDPlayer(CompactDisc cd) {
        this.cd = cd;
    }

    @Override
    public String readCD() {
        return cd.getContents();
    }
}
