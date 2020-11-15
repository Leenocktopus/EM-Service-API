package com.leandoer.service;


import com.leandoer.entity.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {

    Page<ProductModel> getAllProducts(Pageable pageable, String searchString, List<Long> categories, List<Long> manufacturers);

    ProductModel getProductById(Long id);

    ProductModel addProduct(ProductModel product);

    ProductModel modifyProduct(long id, ProductModel product);

    ProductModel deleteProduct(long id);
}
