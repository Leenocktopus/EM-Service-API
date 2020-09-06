package com.leandoer.controller;


import com.leandoer.assembler.ProductAssembler;
import com.leandoer.entity.model.ProductModel;
import com.leandoer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    ProductService productService;
    ProductAssembler assembler;

    @Autowired
    public ProductController(ProductService productService, ProductAssembler assembler) {
        this.productService = productService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<RepresentationModel<ProductModel>> getAllProducts(@PageableDefault Pageable pageable,
                                                                             PagedResourcesAssembler<ProductModel> pagedResourcesAssembler) {

        return assembler.toCollectionModel(productService.getAllProducts(pageable), pagedResourcesAssembler);
    }


    @PostMapping
    public RepresentationModel<ProductModel> addProduct(@RequestBody ProductModel product) {
        return assembler.toModel(productService.addProduct(product));
    }

    @GetMapping("/{id}")
    public RepresentationModel<ProductModel> getProduct(@PathVariable Long id) {
        return assembler.toModel(productService.getProductById(id));
    }


    @PutMapping("/{id}")
    public RepresentationModel<ProductModel> modifyProduct(@PathVariable Long id, @RequestBody ProductModel product) {
        return assembler.toModel(productService.modifyProduct(id, product));
    }


    @DeleteMapping("/{id}")
    public RepresentationModel<ProductModel> deleteProduct(@PathVariable Long id) {
        return assembler.toModel(productService.deleteProduct(id));
    }


}
