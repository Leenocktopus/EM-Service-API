package com.leandoer.service.implementation;

import com.leandoer.entity.dto.ProductDto;
import com.leandoer.exception.IllegalEntityException;
import com.leandoer.repository.*;
import com.leandoer.entity.Product;
import com.leandoer.service.ProductService;
import org.hibernate.TransientPropertyValueException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        return new ProductDto(productRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    public ProductDto addProduct(ProductDto product) {
        if (productRepository.existsById(product.getProductId())){
            throw new RuntimeException();
        }
        productRepository.save(product.toProduct());
        return product;
    }

    @Override
    public ProductDto modifyProduct(long id, ProductDto product) {
        Product selected = productRepository.findById(id).orElse(new Product());
        selected.setName(product.getName());
        selected.setManufacturer(product.getManufacturer().toManufacturer());
        selected.setCategory(product.getCategory().toCategory());
        selected.setPrice(product.getPrice());
        selected.setAmountInStock(product.getAmountInStock());
        selected.setDescr(product.getDescr());
        selected.setPopularity(product.getPopularity());
        selected.setTotalScore(product.getTotalScore());
        try{
            productRepository.save(selected);
        } catch (TransientPropertyValueException ex){
            throw new IllegalEntityException("Product references unsaved transient instance, provide id of a manufacturer or category");
        }

        return new ProductDto(selected);
    }

    @Override
    public ProductDto deleteProduct(long id) {
        Product selected = productRepository.findById(id).orElseThrow(RuntimeException::new);
        productRepository.delete(selected);
        return new ProductDto(selected);
    }
}
