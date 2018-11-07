package com.example.databaseexamples;

import com.example.databaseexamples.entities.Order;
import com.example.databaseexamples.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class BasicQueryCreationTest {

    @Autowired
    private OrderRepository orderRepository;
    private List<Order> manyOrders = new ArrayList<>();

    @Before
    public void createOrder() {
        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setOrderNumber("123A" + i);
            order.setNotes("This is order " + i);
            manyOrders.add(order);
        }
        List<Order> savedOrders = orderRepository.saveAll(manyOrders);
        orderRepository.flush();
    }

    @After
    public void cleanup() {
        log.info("Now comes the delete!");
        orderRepository.deleteAllInBatch();
    }

    @Test
    public void firstQueryCreationTest() {
        Order order = orderRepository.findByOrderNumber("123A10");
        assertNotNull(order);
    }
}
