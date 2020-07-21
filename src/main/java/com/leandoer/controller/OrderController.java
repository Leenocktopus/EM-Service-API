package com.leandoer.controller;

import com.leandoer.entity.dto.OrderDto;
import com.leandoer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public OrderDto add(@RequestBody OrderDto order) {
        return orderService.addOrder(order);
    }

    @GetMapping("/{id}")
    public OrderDto getOneOrder(@PathVariable("id") long id) {
        return orderService.getOneOrder(id);
    }

    @PutMapping("/{id}")
    public OrderDto modifyOrder(@PathVariable("id") long id, @RequestBody OrderDto order) {
        return orderService.modifyOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public OrderDto deleteOrder(@PathVariable("id") long id) {
        return orderService.deleteOrder(id);
    }
}
