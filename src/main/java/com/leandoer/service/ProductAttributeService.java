package com.leandoer.service;

import com.leandoer.entity.dto.ProductAttributeDto;

import java.util.List;

public interface ProductAttributeService {
    List<ProductAttributeDto> getAllProductAttributes(long productId);

    ProductAttributeDto getProductAttribute(long productId, long attributeId);

    ProductAttributeDto addProductAttribute(long productId, ProductAttributeDto attribute);

    ProductAttributeDto modifyProductAttribute(long productId, long attributeId, ProductAttributeDto attribute);

    ProductAttributeDto deleteProductAttribute(long productId, long attributeId);
}
