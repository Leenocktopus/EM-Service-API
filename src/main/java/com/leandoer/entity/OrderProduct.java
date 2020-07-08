package com.leandoer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_product")
@Setter
@Getter
public class OrderProduct {

    @Column(name = "quantity")
    int quantity;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "prod_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


}
