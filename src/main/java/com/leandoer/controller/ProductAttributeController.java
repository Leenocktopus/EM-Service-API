package com.leandoer.controller;

import com.leandoer.entity.dto.ProductAttributeDto;
import com.leandoer.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductAttributeController {


    ProductAttributeService productAttributeService;

    @Autowired
    public ProductAttributeController(ProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    @GetMapping("/{productId}/attributes")
    List<ProductAttributeDto> getAllProductAttributes(@PathVariable("productId") long productId) {
        return productAttributeService.getAllProductAttributes(productId);
    }


    @PostMapping("/{productId}/attributes")
    ProductAttributeDto addProductAttribute(@PathVariable("productId") long productId,
                                            @RequestBody ProductAttributeDto attribute) {
        return productAttributeService.addProductAttribute(productId, attribute);
    }

    @GetMapping("/{productId}/attributes/{attributeId}")
    ProductAttributeDto getProductAttribute(@PathVariable("productId") long productId,
                                            @PathVariable("attributeId") long attributeId) {
        return productAttributeService.getProductAttribute(productId, attributeId);
    }

    @PutMapping("/{productId}/attributes/{attributeId}")
    ProductAttributeDto modifyProductAttribute(@PathVariable("productId") long productId,
                                               @PathVariable("attributeId") long attributeId,
                                               @RequestBody ProductAttributeDto attribute) {
        return productAttributeService.modifyProductAttribute(productId, attributeId, attribute);
    }

    @DeleteMapping("/{productId}/attributes/{attributeId}")
    ProductAttributeDto addProductAttribute(@PathVariable("productId") long productId,
                                            @PathVariable("attributeId") long attributeId) {
        return productAttributeService.deleteProductAttribute(productId, attributeId);
    }

}
