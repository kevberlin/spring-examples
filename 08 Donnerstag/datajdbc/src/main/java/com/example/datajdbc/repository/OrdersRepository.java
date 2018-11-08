package com.example.datajdbc.repository;

import com.example.datajdbc.model.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders, Long> {
}
