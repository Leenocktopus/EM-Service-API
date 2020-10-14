package com.leandoer.service.implementation;

import com.leandoer.entity.Product;
import com.leandoer.entity.model.ProductModel;
import com.leandoer.exception.IllegalEntityException;
import com.leandoer.repository.ProductRepository;
import com.leandoer.service.ProductService;
import org.hibernate.TransientPropertyValueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductModel> getAllProducts(Pageable pageable, String searchString) {

        return (searchString == null || searchString.isEmpty() ?
                productRepository.findAll(pageable) : productRepository.findAllByKeyword(pageable, searchString))
                .map(ProductModel::new);
    }

    @Override
    public ProductModel getProductById(Long id) {
        return new ProductModel(productRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    public ProductModel addProduct(ProductModel product) {
        if (productRepository.existsById(product.getId())) {
            throw new RuntimeException();
        }
        productRepository.save(product.toProduct());
        return product;
    }

    @Override
    public ProductModel modifyProduct(long id, ProductModel product) {
        Product selected = productRepository.findById(id).orElse(new Product());
        selected.setName(product.getName());
        selected.setManufacturer(product.getManufacturer().toManufacturer());
        selected.setCategory(product.getCategory().toCategory());
        selected.setPrice(product.getPrice());
        selected.setAmountInStock(product.getAmountInStock());
        selected.setDescr(product.getDescr());
        selected.setPopularity(product.getPopularity());
        selected.setTotalScore(product.getTotalScore());
        try {
            productRepository.save(selected);
        } catch (TransientPropertyValueException ex) {
            throw new IllegalEntityException("Product references unsaved transient instance, provide id of a manufacturer or category");
        }

        return new ProductModel(selected);
    }

    @Override
    public ProductModel deleteProduct(long id) {
        Product selected = productRepository.findById(id).orElseThrow(RuntimeException::new);
        productRepository.delete(selected);
        return new ProductModel(selected);
    }
}
