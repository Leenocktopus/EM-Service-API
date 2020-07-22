package com.leandoer.service.implementation;

import com.leandoer.entity.Order;
import com.leandoer.entity.model.OrderModel;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.repository.OrderRepository;
import com.leandoer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Page<OrderModel> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderModel::new);
    }

    @Override
    public OrderModel addOrder(OrderModel order) {
        if (orderRepository.existsById(order.getId())) {
            throw new RuntimeException();
        }
        orderRepository.save(order.toOrder());
        return order;
    }

    @Override
    public OrderModel getOneOrder(long id) {
        return new OrderModel(orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order with id: " + id + " has not been found")
        ));
    }

    @Override
    public OrderModel modifyOrder(long id, OrderModel orderDto) {
        Order selected = orderRepository.findById(id).orElse(new Order());
        Order order = orderDto.toOrder();
        selected.setCustomerName(order.getCustomerName());
        selected.setCustomerPhone(order.getCustomerPhone());
        selected.setCustomerEmail(order.getCustomerEmail());
        selected.setDate(order.getDate());
        selected.setOrderStatus(order.getOrderStatus());
        selected.setProducts(order.getProducts());
        return orderDto;
    }

    @Override
    public OrderModel deleteOrder(long id) {
        Order selected = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order with id: " + id + " has not been found")
        );
        orderRepository.delete(selected);
        return new OrderModel(selected);
    }
}
