package com.leandoer.entity.dto;


import com.leandoer.entity.ProductAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Very questionable DTO, because controller code and final JSON representation have not been finalized
@Getter
@Setter
@NoArgsConstructor
public class ProductAttributeDto {
    private String name;
    private String value;

    public ProductAttributeDto(ProductAttribute productAttribute) {
        this.name = productAttribute.getName();
        this.value = productAttribute.getValue();
    }

    public ProductAttribute toProductAttribute(){
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setName(this.name);
        productAttribute.setValue(this.value);
        return productAttribute;
    }
}
