package com.leandoer.service.implementation;

import com.leandoer.entity.Product;
import com.leandoer.entity.ProductAttribute;
import com.leandoer.entity.model.ProductAttributeModel;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.repository.ProductAttributeRepository;
import com.leandoer.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    ProductAttributeRepository productAttributeRepository;

    @Autowired
    public ProductAttributeServiceImpl(ProductAttributeRepository productAttributeRepository) {
        this.productAttributeRepository = productAttributeRepository;
    }

    @Override
    public List<ProductAttributeModel> getAllProductAttributes(long productId) {
        return productAttributeRepository.findAllByProductId(productId).stream()
                .map(ProductAttributeModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public ProductAttributeModel addProductAttribute(long productId, ProductAttributeModel attribute) {
        if (productAttributeRepository.existsById(attribute.getId())) {
            throw new RuntimeException();
        }
        Product id = new Product();
        id.setId(productId);
        ProductAttribute entity = attribute.toProductAttribute();
        entity.setProduct(id);
        productAttributeRepository.save(entity);
        return attribute;
    }

    @Override
    public ProductAttributeModel getProductAttribute(long productId, long attributeId) {
        return new ProductAttributeModel(productAttributeRepository.findByIdAndProductId(attributeId, productId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Attribute with id: " + attributeId + " was not found for product with id: " + productId)
                ));
    }


    @Override
    public ProductAttributeModel modifyProductAttribute(long productId, long attributeId, ProductAttributeModel attribute) {
        ProductAttribute selected = productAttributeRepository.findByIdAndProductId(attributeId, productId)
                .orElse(new ProductAttribute());
        Product id = new Product();
        id.setId(productId);
        selected.setProduct(id);
        selected.setName(attribute.getName());
        selected.setValue(attribute.getValue());
        productAttributeRepository.save(selected);
        return new ProductAttributeModel(selected);
    }

    @Override
    public ProductAttributeModel deleteProductAttribute(long productId, long attributeId) {
        ProductAttribute productAttribute = productAttributeRepository.findByIdAndProductId(attributeId, productId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Attribute with id: " + attributeId + " was not found for product with id: " + productId)
                );
        productAttributeRepository.delete(productAttribute);
        return new ProductAttributeModel(productAttribute);
    }
}
