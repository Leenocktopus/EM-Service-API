package com.leandoer.service;

import com.leandoer.entity.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {


    Page<OrderModel> getAllOrders(Pageable pageable);

    OrderModel addOrder(OrderModel order);

    OrderModel getOneOrder(long id);

    OrderModel modifyOrder(long id, OrderModel orderDto);

    OrderModel deleteOrder(long id);
}
