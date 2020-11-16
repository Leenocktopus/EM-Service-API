package com.leandoer.service.implementation;

import com.leandoer.entity.Product;
import com.leandoer.entity.model.ProductModel;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.exception.IllegalEntityException;
import com.leandoer.repository.ProductAttributeRepository;
import com.leandoer.repository.ProductCommentRepository;
import com.leandoer.repository.ProductRepository;
import com.leandoer.service.ProductService;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductAttributeRepository productAttributeRepository;
    ProductCommentRepository productCommentRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductAttributeRepository productAttributeRepository,
                              ProductCommentRepository productCommentRepository) {
        this.productRepository = productRepository;
        this.productAttributeRepository = productAttributeRepository;
        this.productCommentRepository = productCommentRepository;
    }

    @Override
    public Page<ProductModel> getAllProducts(Pageable pageable, String searchString, List<Long> categories, List<Long> manufacturers) {
        // Ugly fix, but will do for now
        if(categories== null || categories.isEmpty()){
            categories = Collections.singletonList(1L);
        }
        if (manufacturers== null || manufacturers.isEmpty()){
            manufacturers = Collections.singletonList(1L);
        }
        return (searchString == null || searchString.isEmpty() ?
                productRepository.findAll(pageable, categories, manufacturers) : productRepository.findAllByKeyword(pageable, searchString))
                .map(ProductModel::new);
    }

    @Override
    public ProductModel getProductById(Long id) {
        return new ProductModel(productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product with id: " + id + " has not been found"))
        );
    }

    @Override
    public ProductModel addProduct(ProductModel product) {
        Product saved = productRepository.save(product.toProduct());
        return new ProductModel(productRepository.findById(saved.getId()).orElseThrow(
                () -> new EntityNotFoundException("Product with id: " + saved.getId() + " has not been found"))
        );
    }

    @Override
    public ProductModel modifyProduct(long id, ProductModel product) {
        product.setId(id);
        try {
            return addProduct(product);
        } catch (TransientPropertyValueException ex) {
            throw new IllegalEntityException("Product references unsaved transient instance, provide id of a manufacturer or category");
        }
    }
    @Transactional
    @Override
    public ProductModel deleteProduct(long id) {
        productAttributeRepository.deleteProductAttributeByProductId(id);
        productCommentRepository.deleteProductCommentByProductId(id);
        Product selected = productRepository.findById(id).orElseThrow(RuntimeException::new);
        productRepository.delete(selected);
        return new ProductModel(selected);
    }
}
