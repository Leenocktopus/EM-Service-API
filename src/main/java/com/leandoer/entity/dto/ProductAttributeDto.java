package com.leandoer.entity.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.ProductAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductAttributeDto {
    private long id;
    private String name;
    private String value;

    public ProductAttributeDto(ProductAttribute productAttribute) {
        this.id = productAttribute.getId();
        this.name = productAttribute.getName();
        this.value = productAttribute.getValue();
    }

    public ProductAttribute toProductAttribute() {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(id);
        productAttribute.setName(this.name);
        productAttribute.setValue(this.value);
        return productAttribute;
    }
}
