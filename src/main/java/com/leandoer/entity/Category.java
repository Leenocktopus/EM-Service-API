package com.leandoer.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "ecommerce", name = "categories")
@Setter
@Getter
@ToString
public class Category extends BaseEntity {

    @Column(name = "name")
    private String name;
}
