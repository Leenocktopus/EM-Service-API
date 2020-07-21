package com.leandoer.service;

import com.leandoer.entity.dto.OrderDto;

import java.util.List;

public interface OrderService {


    List<OrderDto> getAllOrders();

    OrderDto addOrder(OrderDto order);

    OrderDto getOneOrder(long id);

    OrderDto modifyOrder(long id, OrderDto orderDto);

    OrderDto deleteOrder(long id);
}
