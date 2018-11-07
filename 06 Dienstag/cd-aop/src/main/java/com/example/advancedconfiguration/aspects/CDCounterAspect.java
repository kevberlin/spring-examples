package com.example.advancedconfiguration.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;


@Aspect
public class CDCounterAspect {

    private int counter = 0;

    @AfterReturning(
            pointcut = "execution(* com.example.advancedconfiguration.CompactDisc.getContents())",
            returning = "songTitle"
    )
    public void cdPlayed(String songTitle) {

        counter++;

        System.out.println("===========================");
        System.out.println("CD is playing: " + songTitle);
        System.out.println("CDs played so far: " + counter);
    }
}
