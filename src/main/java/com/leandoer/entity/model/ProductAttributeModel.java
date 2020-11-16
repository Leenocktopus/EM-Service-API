package com.leandoer.entity.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leandoer.entity.Product;
import com.leandoer.entity.ProductAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"links"})
public class ProductAttributeModel extends RepresentationModel<ProductAttributeModel> {
    private Long id;
    private String name;
    private String value;
    @JsonIgnore
    private long productId = 0;

    public ProductAttributeModel(ProductAttribute productAttribute) {
        this.id = productAttribute.getId();
        this.name = productAttribute.getName();
        this.value = productAttribute.getValue();
        this.productId = productAttribute.getProduct().getId();
    }

    public ProductAttribute toProductAttribute() {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(id);
        productAttribute.setName(this.name);
        productAttribute.setValue(this.value);
        Product product = new Product();
        product.setId(this.productId);
        productAttribute.setProduct(product);
        return productAttribute;
    }
}
