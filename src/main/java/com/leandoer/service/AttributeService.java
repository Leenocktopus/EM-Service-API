package com.leandoer.service;

import com.leandoer.entity.ProductAttribute;

import java.util.List;

public interface AttributeService {
    List<ProductAttribute> getAllByProductId(String id);

    ProductAttribute add(ProductAttribute attribute);
}
