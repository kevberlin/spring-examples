package com.example.databaseexamples;

import com.example.databaseexamples.entities.Order;
import com.example.databaseexamples.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PagingAndSortingRepositoryTest {
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
    }

    @Test
    public void basicSortingTest() {
// we will see batching in action here
        orderRepository.saveAll(manyOrders);
// we can load all orders by orderNumber
        Iterable<Order> reloadedOrders =
                orderRepository.findAll(Sort.by("orderNumber"));
// we can load all orders by orderNumber descending
        Iterable<Order> reloadedOrders2 =
                orderRepository.findAll(Sort.by(Sort.Direction.DESC, "orderNumber"));
// and finally we delete them (watch for the batch!)
        orderRepository.deleteAll(reloadedOrders);
    }

    @Test
    public void basicPagingTest() {
        orderRepository.saveAll(manyOrders);
        log.info("Now comes the select!");
// we can load page 0 of size 10 of our orders
        Iterable<Order> reloadedOrders =
                orderRepository.findAll(PageRequest.of(0, 10));
        log.info("Now comes the delete!");
        orderRepository.deleteAll();
    }

    @Test
    public void basicPagingAndSortingTest() {
// this will create the ids on our orders
        orderRepository.saveAll(manyOrders);
        log.info("Now comes the select!");
// we can load page 1 of size 10 of our orders
        Iterable<Order> reloadedOrders =
                orderRepository.findAll(
                        PageRequest.of(1, 10, Sort.by("orderNumber")));
        log.info("Now comes the delete!");
        orderRepository.deleteAll();
    }

}
