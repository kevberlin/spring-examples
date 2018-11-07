package com.example.basicconfiguration;

import com.example.basicconfiguration.app.CompactDisc;
import com.example.basicconfiguration.config.CompactDiscConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CompactDiscConfig.class)
public class FirstCompactDiscTest {

    @Autowired
    private CompactDisc cd;

    @Test
    public void testCD() {
        assertNotNull(cd);
        assertEquals("Achtung Baby", cd.getContents());
    }
}