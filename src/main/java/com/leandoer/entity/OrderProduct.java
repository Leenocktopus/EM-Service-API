package com.leandoer.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_product")
@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class OrderProduct {

    @Column(name = "quantity")
    int quantity;
    @EmbeddedId
    private OrderProductId id;
    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "prod_id")
    private Product product;
    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    public OrderProduct(Product product, Order order, int quantity) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.id = new OrderProductId(product.getId(), order.getId());
    }
}
