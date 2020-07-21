package com.leandoer.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Setter
@Getter
@ToString
public class Product extends BaseEntity {

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

    @Column(name = "popularity")
    private int popularity;
    @Column(name = "total_score")
    private float totalScore;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductAttribute> productAttributes = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductComment> productComments = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orders = new ArrayList<>();
}
