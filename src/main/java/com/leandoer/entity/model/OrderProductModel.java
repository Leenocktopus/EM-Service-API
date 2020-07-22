package com.leandoer.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.OrderProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"links"}, ignoreUnknown = true)
public class OrderProductModel {
    int quantity;
    ProductModel product = new ProductModel();

    public OrderProductModel(OrderProduct orderProduct) {
        this.quantity = orderProduct.getQuantity();
        this.product = new ProductModel(orderProduct.getProduct());
    }

    public OrderProduct toOrderProduct() {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setQuantity(this.quantity);
        orderProduct.setProduct(this.product.toProduct());
        return orderProduct;
    }
}
