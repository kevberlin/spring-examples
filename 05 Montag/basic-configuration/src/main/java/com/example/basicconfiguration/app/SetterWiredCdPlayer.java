package com.example.basicconfiguration.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetterWiredCdPlayer implements CDPlayer {
    private CompactDisc cd;

    @Autowired
    public void setCd(CompactDisc cd) {
        this.cd = cd;
    }

    @Override
    public String readCD() {
        return "Setter: " + cd.getContents();
    }
}
