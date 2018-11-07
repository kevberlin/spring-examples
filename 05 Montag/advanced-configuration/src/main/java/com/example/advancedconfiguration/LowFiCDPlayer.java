package com.example.advancedconfiguration;
import org.springframework.beans.factory.annotation.Autowired;

public class LowFiCDPlayer implements CDPlayer {

    private CompactDisc cd;

    @Autowired
    public LowFiCDPlayer(CompactDisc cd) {
        this.cd = cd;
    }

    @Override
    public String readCD() {
        return cd.getContents();
    }
}
