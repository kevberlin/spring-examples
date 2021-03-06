package com.example.basicconfiguration;

import com.example.basicconfiguration.app.CDPlayer;
import com.example.basicconfiguration.config.CdScanConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CdScanConfig.class)
public class ScanForSetterWiredCdPlayerTest {

    @Qualifier("setterWiredCdPlayer")
    @Autowired
    private CDPlayer player;

    @Test
    public void testPlayer() {
        assertNotNull(player);
        assertEquals("Setter: The Joshua Tree", player.readCD());
    }
}
