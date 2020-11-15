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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public CollectionModel<RepresentationModel<ProductModel>>
    getAllProducts(@PageableDefault Pageable pageable,
                   PagedResourcesAssembler<ProductModel> pagedResourcesAssembler,
                   @RequestParam(value = "search", required = false) String searchString,
                   @RequestParam(value = "f_cat", required = false) List<Long> categories,
                   @RequestParam(value = "f_man", required = false) List<Long> manufacturers) {
        return assembler.toCollectionModel(
                productService.getAllProducts(pageable, searchString, categories, manufacturers),
                pagedResourcesAssembler
        );
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentationModel<ProductModel> addProduct(HttpServletResponse response,
                                                        @RequestBody ProductModel product) {
        RepresentationModel<ProductModel> newProduct = assembler.toModel(productService.addProduct(product));
        response.addHeader(HttpHeaders.LOCATION, newProduct.getLink("self").get().getHref());
        return newProduct;
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
