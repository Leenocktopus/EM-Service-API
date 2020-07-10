package com.leandoer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Embeddable
public class OrderProductId implements Serializable {
    long orderId;
    long productId;

    public OrderProductId() {
    }
}
