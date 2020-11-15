package com.leandoer.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leandoer.entity.OrderProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"links"})
public class OrderProductModel extends RepresentationModel<OrderProductModel> {
    private int quantity;
    private ProductModel product = new ProductModel();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long order;
    public OrderProductModel(OrderProduct orderProduct) {
        this.quantity = orderProduct.getQuantity();
        this.product = new ProductModel(orderProduct.getProduct());
        this.order = orderProduct.getOrder().getId();
    }

    public OrderProduct toOrderProduct() {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setQuantity(this.quantity);
        orderProduct.setProduct(this.product.toProduct());
        return orderProduct;
    }
}
