package com.leandoer.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(schema = "ecommerce",
        name = "product_comments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"prod_id", "user"})})
@Setter
@Getter
@ToString
public class ProductComment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id")
    private Product product;

    @Column(name = "user")
    private String user;

    @Enumerated(EnumType.STRING)
    @Column(name = "score")
    private Score score;

    @Column(name = "comment_text")
    private String text;

    @Column(name = "comment_date")
    private Timestamp date;
}
