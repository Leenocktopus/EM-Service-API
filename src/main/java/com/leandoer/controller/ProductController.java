package com.leandoer.controller;



import com.leandoer.entity.Product;
import com.leandoer.entity.dto.ProductDto;
import com.leandoer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping
    public ProductDto addProduct(@RequestBody ProductDto product){
        return productService.addProduct(product);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable long id){
        return productService.getProductById(id);
    }
    @PutMapping("/{id}")
    public ProductDto modifyProduct(@PathVariable long id, @RequestBody ProductDto product){
        return productService.modifyProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable long id){
        return productService.deleteProduct(id);
    }








}
