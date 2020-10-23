package com.leandoer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@Embeddable
public class OrderProductId implements Serializable {
    long orderId;
    long productId;

    public OrderProductId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductId obj = (OrderProductId) o;
        return Objects.equals(orderId, obj.orderId) &&
                Objects.equals(productId, obj.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }

}
