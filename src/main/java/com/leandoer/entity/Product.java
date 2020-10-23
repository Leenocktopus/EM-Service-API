package com.leandoer.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(schema = "ecommerce", name = "products")
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


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OrderProduct> orders = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product obj = (Product) o;
        return Objects.equals(getId(), obj.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
