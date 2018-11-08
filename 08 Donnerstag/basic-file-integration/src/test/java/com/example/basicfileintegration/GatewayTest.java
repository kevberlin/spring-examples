package com.example.basicfileintegration;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayTest {

    @Autowired
    private UpperCaseGateway gateway;

    @Test
    public void testGateway() {
        String result = gateway.uppercase("Hello");
        Assertions.assertThat(result).isEqualTo("HELLO");
    }
}
