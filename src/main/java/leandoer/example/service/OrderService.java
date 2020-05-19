package leandoer.example.service;

import leandoer.example.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrders();

    void addOrder(Order order);
}
