package com.example.datajdbc;

import com.example.datajdbc.model.Orders;
import com.example.datajdbc.repository.OrdersRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersTest {

    @Autowired
    private OrdersRepository ordersRepository;

    @After
    public void cleanup() {
        ordersRepository.deleteAll();
    }

    @Test
    public void testSave() {
        Orders orders = new Orders(null, 0, "1234", "text");
        ordersRepository.save(orders);
    }

    @Test
    public void testFind() {
        Orders orders = new Orders(null, 0, "1234", "text");
        ordersRepository.save(orders);
        Optional<Orders> result = ordersRepository.findById(orders.getId());
        Assert.assertTrue(result.isPresent());
        result.ifPresent(o -> {
            Assert.assertEquals("text", o.getNotes());
            Assert.assertNotNull(o.getId());
        });
    }
}
