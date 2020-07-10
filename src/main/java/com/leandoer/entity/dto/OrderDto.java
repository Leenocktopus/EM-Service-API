package com.leandoer.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.Order;
import com.leandoer.entity.OrderProduct;
import com.leandoer.entity.OrderStatus;
import com.leandoer.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {
    private long orderId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private Timestamp date;
    private OrderStatus orderStatus;

    private List<OrderProductDto> products;

    public OrderDto(Order order) {
        this.orderId = order.getOrderId();
        this.customerName = order.getCustomerName();
        this.customerPhone = order.getCustomerPhone();
        this.customerEmail = order.getCustomerEmail();
        this.date = order.getDate();
        this.orderStatus = order.getOrderStatus();
        this.products = order.getProducts().stream()
                .map(OrderProductDto::new)
                .collect(Collectors.toList());
    }

    public Order toOrder(){
        Order order = new Order();
        order.setOrderId(this.orderId);
        order.setCustomerName(this.customerName);
        order.setCustomerPhone(this.customerPhone);
        order.setCustomerEmail(this.customerEmail);
        order.setDate(this.date);
        order.setOrderStatus(this.orderStatus);
        this.products.stream()
                .map(OrderProductDto::toOrderProduct)
                .forEach(orderProduct -> order.addProduct(orderProduct.getProduct(), orderProduct.getQuantity()));
        return order;
    }

}
