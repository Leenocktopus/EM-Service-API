package com.leandoer.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product_attributes")
@Setter
@Getter
@ToString
public class ProductAttribute extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id")
    private Product product;

    @Column(name = "attribute")
    private String name;

    @Column(name = "value")
    private String value;
}
