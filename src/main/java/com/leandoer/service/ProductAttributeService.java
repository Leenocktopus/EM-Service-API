package com.leandoer.service;

import com.leandoer.entity.model.ProductAttributeModel;

import java.util.List;

public interface ProductAttributeService {
    List<ProductAttributeModel> getAllProductAttributes(long productId);

    ProductAttributeModel getProductAttribute(long productId, long attributeId);

    ProductAttributeModel addProductAttribute(long productId, ProductAttributeModel attribute);

    ProductAttributeModel modifyProductAttribute(long productId, long attributeId, ProductAttributeModel attribute);

    ProductAttributeModel deleteProductAttribute(long productId, long attributeId);
}
