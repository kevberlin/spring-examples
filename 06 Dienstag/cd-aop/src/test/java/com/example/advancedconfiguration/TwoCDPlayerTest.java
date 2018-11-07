package com.example.advancedconfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("lowfi")
public class TwoCDPlayerTest {

    @Autowired
    private CDPlayer player;

    @Autowired
    @Qualifier("player2")
    private CDPlayer player2;

    @Test
    public void getContents() {
        assertNotNull(player);
        assertEquals("War", player.readCD());

        assertNotNull(player2);
        assertEquals("Achtung Baby", player2.readCD());
    }
}