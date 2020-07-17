package com.leandoer.controller;

import com.leandoer.entity.ProductComment;
import com.leandoer.entity.dto.ProductCommentDto;
import com.leandoer.service.ProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductCommentController {

    ProductCommentService productCommentService;

    @Autowired
    public ProductCommentController(ProductCommentService productCommentService) {
        this.productCommentService = productCommentService;
    }




    @GetMapping("/{productId}/comments")
    List<ProductCommentDto> getAllProductComments(@PathVariable("productId") long productId) {

        return productCommentService.getAllProductComments(productId);
    }


    @PostMapping("/{productId}/comments")
    ProductCommentDto addProductComment(@PathVariable("productId") long productId,
                                        @RequestBody ProductCommentDto comment) {
        return productCommentService.addProductComment(productId, comment);
    }


    @GetMapping("/{productId}/comments/{commentId}")
    ProductCommentDto getProductComment(@PathVariable("productId") long productId,
                                        @PathVariable("commentId") long commentId) {
        return productCommentService.getProductComment(productId, commentId);
    }

    @PutMapping("/{productId}/comments/{commentId}")
    ProductCommentDto modifyProductComment(@PathVariable("productId") long productId,
                                           @PathVariable("commentId") long commentId,
                                           @RequestBody ProductCommentDto comment) {
        return productCommentService.modifyProductComment(productId, commentId, comment);
    }

    @DeleteMapping("/{productId}/comments/{commentId}")
    ProductCommentDto deleteProductComment(@PathVariable("productId") long productId,
                                           @PathVariable("commentId") long commentId) {
        return productCommentService.deleteProductComment(productId, commentId);
    }



}
