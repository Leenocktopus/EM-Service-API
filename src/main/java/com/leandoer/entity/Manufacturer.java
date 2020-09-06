package com.leandoer.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "ecommerce", name = "manufacturers")
@Setter
@Getter
@ToString
public class Manufacturer extends BaseEntity {

    @Column(name = "name")
    private String name;
}
