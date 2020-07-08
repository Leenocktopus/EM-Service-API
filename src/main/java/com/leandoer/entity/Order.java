package com.leandoer.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;

    @Column(name = "cust_name")
    private String customerName;

    @Column(name = "cust_phone")
    private String customerPhone;

    @Column(name = "cust_email")
    private String customerEmail;

    @Column(name = "order_date")
    private Date date;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "order")
    private List<OrderProduct> products = new ArrayList<>();

}
