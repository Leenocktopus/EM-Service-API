package com.leandoer.service.implementation;

import com.leandoer.entity.dto.OrderDto;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.service.OrderService;
import com.leandoer.entity.Order;
import com.leandoer.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @Override
    public OrderDto addOrder(OrderDto order) {
        if (orderRepository.existsById(order.getId())){
            throw new RuntimeException();
        }
        orderRepository.save(order.toOrder());
        return order;
    }

    @Override
    public OrderDto getOneOrder(long id) {
        return new OrderDto(orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order with id: "+id+" has not been found")
        ));
    }

    @Override
    public OrderDto modifyOrder(long id, OrderDto orderDto) {
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
    public OrderDto deleteOrder(long id) {
        Order selected = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order with id: "+id+" has not been found")
        );
        orderRepository.delete(selected);
        return new OrderDto(selected);
    }
}
