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
@ActiveProfiles("production")
public class BlankDiscProductionTest {

    @Autowired
    private CompactDisc cd;

    @Test
    public void getContents() {
        assertNotNull(cd);
        assertEquals("Wide awake in America", cd.getContents());
    }
}