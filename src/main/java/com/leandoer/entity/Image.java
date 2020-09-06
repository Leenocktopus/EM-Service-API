package com.leandoer.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"product"})
@Table(schema = "ecommerce", name = "images")
public class Image extends BaseEntity {

    @Column(name = "image")
    private String filename;
    @ManyToOne
    @JoinColumn(name = "prod_id")
    private Product product;
}
