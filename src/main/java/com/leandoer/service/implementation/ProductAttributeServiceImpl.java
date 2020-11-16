package com.leandoer.service.implementation;

import com.leandoer.entity.ProductAttribute;
import com.leandoer.entity.model.ProductAttributeModel;
import com.leandoer.exception.EntityConflictException;
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
        checkForDuplicate(productId, attribute);
        attribute.setProductId(productId);
        return new ProductAttributeModel(productAttributeRepository.save(attribute.toProductAttribute()));
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
        checkForDuplicate(productId, attribute);
        attribute.setId(attributeId);
        attribute.setProductId(productId);
        return new ProductAttributeModel(productAttributeRepository.save(attribute.toProductAttribute()));
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

    private void checkForDuplicate(long productId, ProductAttributeModel attribute) {
        if (productAttributeRepository.existsByProductIdAndName(productId, attribute.getName())) {
            throw new EntityConflictException("Attribute with name '" + attribute.getName() + "' already exists for product with id: " + productId);
        }
    }
}
