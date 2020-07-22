package com.leandoer.service;


import com.leandoer.entity.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    Page<ProductModel> getAllProducts(Pageable pageable);

    ProductModel getProductById(Long id);

    ProductModel addProduct(ProductModel product);

    ProductModel modifyProduct(long id, ProductModel product);

    ProductModel deleteProduct(long id);
}
