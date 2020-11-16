package com.leandoer.service.implementation;

import com.leandoer.entity.Order;
import com.leandoer.entity.model.CategoryModel;
import com.leandoer.entity.model.OrderModel;
import com.leandoer.exception.EntityConflictException;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.repository.OrderRepository;
import com.leandoer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


	@Override
	public Page<OrderModel> getAllOrders(Pageable pageable, String searchString) {
		return (searchString == null || searchString.isEmpty() ?
				orderRepository.findAll(pageable) : orderRepository.findAllByKeyword(pageable, searchString))
				.map(OrderModel::new);
	}

    @Override
    public OrderModel addOrder(OrderModel order) {
	    Order newOrder = orderRepository.save(order.toOrder());
	    order.getProducts().forEach(product -> newOrder.addProduct(product.getProduct().toProduct(), product.getQuantity()));
	    orderRepository.save(newOrder);
	    return new OrderModel(orderRepository.save(newOrder));
    }

    @Override
    public OrderModel getOneOrder(long id) {
        return new OrderModel(orderRepository.findById(id).orElseThrow(
		        () -> new EntityNotFoundException("Order with id: " + id + " has not been found")));
    }

    @Override
    public OrderModel modifyOrder(long id, OrderModel order) {
        order.setId(id);
	    return new OrderModel(orderRepository.save(order.toOrder()));
    }

    @Override
    public OrderModel deleteOrder(long id) {
        Order selected = orderRepository.findById(id).orElseThrow(
        		() -> new EntityNotFoundException("Order with id: " + id + " has not been found"));
        orderRepository.delete(selected);
        return new OrderModel(selected);
    }
    
}
