package com.leandoer.service;


import com.leandoer.entity.dto.ProductDto;


import java.util.List;


public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    ProductDto addProduct(ProductDto product);

    ProductDto modifyProduct(long id, ProductDto product);

    ProductDto deleteProduct(long id);
}
