package com.leandoer.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;


@Entity
@Table(name = "product_comments")
@Setter
@Getter
@ToString
public class ProductComment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

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
