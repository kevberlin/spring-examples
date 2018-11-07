package com.example.databaseexamples;

import com.example.databaseexamples.entities.Order;
import com.example.databaseexamples.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class JPARepositoryTest {

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
// this will create the ids on our orders.
        List<Order> savedOrders = orderRepository.saveAll(manyOrders);
        orderRepository.flush();
    }

    @Test
    public void basicCrudCollectionTest() {
        log.info("Now comes the select!");
// we can load all orders, notice that we get a List now!
        List<Order> reloadedOrders = orderRepository.findAll();
    }

    @Test
    @Transactional
    public void basicFlushTest() {
        Order firstOrder = manyOrders.get(0);
        log.info("Now comes the save!");
        firstOrder.setNotes("Update1");
        orderRepository.save(firstOrder);
        log.info("Now comes the flush, surprise!!");
        orderRepository.flush();
    }

    @Test
    @Transactional
    public void basicSaveAndFlushTest() {
        Order firstOrder = manyOrders.get(0);
        firstOrder.setNotes("Update1");
        orderRepository.saveAndFlush(firstOrder);
    }

    @Test(expected = LazyInitializationException.class)
    public void basicGetOneTest() {
        Long firstId = manyOrders.get(0).getId();
        Order reference = orderRepository.getOne(firstId);
        log.info("Now comes the access!");
        System.out.println(reference.getOrderNumber());
    }

    @Test
    public void queryByExampleTest() {
        Order example = new Order();
        example.setOrderNumber("123A4");
        List<Order> orders = orderRepository.findAll(Example.of(example));
        Assert.assertEquals(1, orders.size());
        orders = orderRepository.findAll(Example.of(example),
                Sort.by("orderNumber"));
        Assert.assertEquals(1, orders.size());
    }

    @Test
    public void queryByExampleMatcherTest() {
        Order example = new Order();
        example.setOrderNumber("123a");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("orderNumber",
                        match -> match.startsWith().ignoreCase());
        long count = orderRepository.count(Example.of(example, matcher));
        Assert.assertEquals(100, count);
    }

    @Test
    public void queryByExampleMatcherTest2() {
        Order example = new Order();
        example.setOrderNumber("123A4");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIncludeNullValues()
                .withIgnorePaths("notes", "id");
        long count = orderRepository.count(Example.of(example, matcher));
        Assert.assertEquals(1, count);
    }

    @After
    public void cleanup() {
// notice the difference!
        log.info("Now comes the delete!");
        orderRepository.deleteAllInBatch();
    }
}
