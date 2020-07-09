package com.leandoer.entity.dto;

import com.leandoer.entity.Order;
import com.leandoer.entity.OrderProduct;
import com.leandoer.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private long orderId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private Date date;
    private OrderStatus orderStatus;
    private List<OrderProduct> products;

    public OrderDto(Order order) {
        this.orderId = order.getOrderId();
        this.customerName = order.getCustomerName();
        this.customerPhone = order.getCustomerPhone();
        this.customerEmail = order.getCustomerEmail();
        this.date = order.getDate();
        this.orderStatus = order.getOrderStatus();
        this.products = order.getProducts();
    }

    public Order toOrder(){
        Order order = new Order();
        order.setOrderId(this.orderId);
        order.setCustomerName(this.customerName);
        order.setCustomerPhone(this.customerPhone);
        order.setCustomerEmail(this.customerEmail);
        order.setDate(this.date);
        order.setOrderStatus(this.orderStatus);
        order.setProducts(this.products);
        return order;
    }

}
