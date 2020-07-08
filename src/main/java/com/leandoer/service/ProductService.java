package com.leandoer.service;

import com.leandoer.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<Product> getAllProducts(Map<String, List<String>> params, Pageable pageable);

    Product getProductById(String id);

    List<Product> findByKeyword(String keyword, Pageable pageable);


}
