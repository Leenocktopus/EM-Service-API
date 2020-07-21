package com.leandoer.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {


    @Column(name = "cust_name")
    private String customerName;

    @Column(name = "cust_phone")
    private String customerPhone;

    @Column(name = "cust_email")
    private String customerEmail;

    @Column(name = "order_date")
    private Timestamp date;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> products = new ArrayList<>();


    public void addProduct(Product product, int quantity) {
        OrderProduct op = new OrderProduct(product, this, quantity);
        products.add(op);
        product.getOrders().add(op);
    }

    public void removeProduct(Product product) {
        for (Iterator<OrderProduct> iterator = products.iterator(); iterator.hasNext(); ) {
            OrderProduct op = iterator.next();
            if (op.getProduct().equals(product) && op.getOrder().equals(this)) {
                iterator.remove();
                op.getProduct().getOrders().remove(op);
                op.setOrder(null);
                op.setProduct(null);
            }
        }
    }

}
