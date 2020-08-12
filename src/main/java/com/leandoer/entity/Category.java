package com.leandoer.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Setter
@Getter
@ToString
public class Category extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "parent")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Category> subCategories = new HashSet<>();
}
