package com.leandoer.controller;

import com.leandoer.assembler.ProductAttributeAssembler;
import com.leandoer.entity.model.ProductAttributeModel;
import com.leandoer.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductAttributeController {

    ProductAttributeService productAttributeService;
    ProductAttributeAssembler assembler;

    @Autowired
    public ProductAttributeController(ProductAttributeService productAttributeService, ProductAttributeAssembler assembler) {
        this.productAttributeService = productAttributeService;
        this.assembler = assembler;
    }

    @GetMapping("/{productId}/attributes")
    public CollectionModel<RepresentationModel<ProductAttributeModel>> getAllProductAttributes(@PathVariable("productId") Long productId) {
        return assembler.toCollectionModel(productAttributeService.getAllProductAttributes(productId), productId);
    }

    @PostMapping("/{productId}/attributes")
    public RepresentationModel<ProductAttributeModel> addProductAttribute(@PathVariable("productId") Long productId,
                                                                          @RequestBody ProductAttributeModel attribute) {
        return assembler.toModel(productAttributeService.addProductAttribute(productId, attribute));
    }

    @GetMapping("/{productId}/attributes/{attributeId}")
    public RepresentationModel<ProductAttributeModel> getProductAttribute(@PathVariable("productId") Long productId,
                                                                          @PathVariable("attributeId") Long attributeId) {
        return assembler.toModel(productAttributeService.getProductAttribute(productId, attributeId));
    }

    @PutMapping("/{productId}/attributes/{attributeId}")
    public RepresentationModel<ProductAttributeModel> modifyProductAttribute(@PathVariable("productId") Long productId,
                                                                             @PathVariable("attributeId") Long attributeId,
                                                                             @RequestBody ProductAttributeModel attribute) {
        return assembler.toModel(productAttributeService.modifyProductAttribute(productId, attributeId, attribute));
    }

    @DeleteMapping("/{productId}/attributes/{attributeId}")
    public RepresentationModel<ProductAttributeModel> addProductAttribute(@PathVariable("productId") Long productId,
                                                                          @PathVariable("attributeId") Long attributeId) {
        return assembler.toModel(productAttributeService.deleteProductAttribute(productId, attributeId));
    }

}
