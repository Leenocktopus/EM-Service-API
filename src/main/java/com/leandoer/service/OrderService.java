package com.leandoer.service;

import com.leandoer.entity.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {


    Page<OrderModel> getAllOrders(Pageable pageable, String searchString);

    OrderModel addOrder(OrderModel order);

    OrderModel getOneOrder(long id);

    OrderModel modifyOrder(long id, OrderModel orderDto);

    OrderModel deleteOrder(long id);
}
