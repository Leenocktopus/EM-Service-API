package com.leandoer.service;

import com.leandoer.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrders();

    void addOrder(Order order);
}
