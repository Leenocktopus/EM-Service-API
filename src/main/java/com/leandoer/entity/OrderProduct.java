package com.leandoer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(schema = "ecommerce", name = "order_product")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class OrderProduct {

    @Column(name = "quantity")
    int quantity;
    @EmbeddedId
    private OrderProductId id;
    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id")
    private Product product;
    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    public OrderProduct(Product product, Order order, int quantity) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.id = new OrderProductId(product.getId(), order.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct obj = (OrderProduct) o;
        return Objects.equals(order, obj.order) &&
                Objects.equals(product, obj.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
