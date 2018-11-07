package com.example.advancedconfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("hifi")
public class CDPlayerTest {

    @Autowired
    private CDPlayer player;

    @Test
    public void getContents() {
        assertNotNull(player);
        assertEquals("WAR", player.readCD());
    }
}