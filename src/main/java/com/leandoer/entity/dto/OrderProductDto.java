package com.leandoer.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.OrderProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderProductDto {
    int quantity;
    ProductDto product = new ProductDto();

    public OrderProductDto(OrderProduct orderProduct) {
        this.quantity = orderProduct.getQuantity();
        this.product = new ProductDto(orderProduct.getProduct());
    }

    public OrderProduct toOrderProduct() {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setQuantity(this.quantity);
        orderProduct.setProduct(this.product.toProduct());
        return orderProduct;
    }
}
