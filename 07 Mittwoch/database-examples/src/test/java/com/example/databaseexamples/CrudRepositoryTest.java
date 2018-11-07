package com.example.databaseexamples;

import com.example.databaseexamples.entities.Order;
import com.example.databaseexamples.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CrudRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    private Order order;
    private List<Order> manyOrders = new ArrayList<>();

    @Before
    public void createOrder() {
        order = new Order();
        order.setOrderNumber("123A");
        order.setNotes("This is my first order!");
        Order order2 = new Order();
        order2.setOrderNumber("123B");
        order2.setNotes("This is my second order!");
        manyOrders.add(order);
        manyOrders.add(order2);
    }

    @Test
    public void basicCrudTest() {
// this will create the id on our order
        orderRepository.save(order);
// check for existence
        boolean exists = orderRepository.existsById(order.getId());
// we can load an order by id
        Optional<Order> reloadedOrder = orderRepository.findById(order.getId());
        Assert.assertTrue(reloadedOrder.isPresent());
// we can count them
        long count = orderRepository.count();
        log.info("Now comes the delete!");
// and finally we delete it
        orderRepository.delete(reloadedOrder.get());
    }

    @Test
    public void basicCrudCollectionTest() {
// this will create the ids on our orders
        orderRepository.saveAll(manyOrders);
// we can load all orders
        Iterable<Order> reloadedOrders = orderRepository.findAll();
// and finally we delete them
        orderRepository.deleteAll(reloadedOrders);
    }

    @Test
    public void basicCrudCollectionIdTest() {
// this will create the ids on our orders
        orderRepository.saveAll(manyOrders);
        List<Long> ids = manyOrders.stream().map(o ->
                o.getId()).collect(Collectors.toList());
// we can load all orders
        Iterable<Order> reloadedOrders = orderRepository.findAllById(ids);
// and finally we delete them by id
        ids.forEach(id -> orderRepository.deleteById(id));
    }

    @Test
    public void basicCrudDeleteAllTest() {
// this will create the ids on our orders
        orderRepository.saveAll(manyOrders);
        log.info("Now comes the delete!");
        orderRepository.deleteAll();
    }
}
