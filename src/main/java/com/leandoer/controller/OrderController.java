package com.leandoer.controller;

import com.leandoer.service.OrderService;
import com.leandoer.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> orders() {
        return orderService.getOrders();
    }

    @PostMapping
    public void add(@RequestBody Order order) {
        orderService.addOrder(order);
    }
}
