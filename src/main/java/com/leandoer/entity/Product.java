package com.leandoer.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private Set<Image> images = new HashSet<>();


    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private Set<OrderProduct> orders = new HashSet<>();
}
