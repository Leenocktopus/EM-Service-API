package com.leandoer.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Setter
@Getter
public class Product {

    @Id
    @Column(name = "prod_id")
    private String productId;

    @Column(name = "product_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manuf_id")
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private Category category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "amnt_in_stock")
    private int amountInStock;

    @Column(name = "descr")
    private String descr;

    @Column(name = "total_score")
    private float totalScore;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductAttribute> productAttributes = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductComment> productComments = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> orders = new HashSet<>();
}
