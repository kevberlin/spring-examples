package com.example.advancedconfiguration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("achtungbaby")
public class AchtungBaby implements CompactDisc {

    @Override
    public String getContents() {
        return "Achtung Baby";
    }
}
