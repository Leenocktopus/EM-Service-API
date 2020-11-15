package com.leandoer.controller;

import com.leandoer.assembler.ProductCommentAssembler;
import com.leandoer.entity.model.ProductCommentModel;
import com.leandoer.service.ProductCommentService;
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

@RestController
@RequestMapping("/api/v1/products")
public class ProductCommentController {

    ProductCommentService productCommentService;
    ProductCommentAssembler assembler;

    @Autowired
    public ProductCommentController(ProductCommentService productCommentService, ProductCommentAssembler assembler) {
        this.productCommentService = productCommentService;
        this.assembler = assembler;
    }

    @GetMapping("/{productId}/comments")
    public CollectionModel<RepresentationModel<ProductCommentModel>>
    getAllProductComments(@PathVariable("productId") Long productId,
                          @PageableDefault Pageable pageable,
                          PagedResourcesAssembler<ProductCommentModel> pagedResourcesAssembler) {
        return assembler.toCollectionModel(
                productCommentService.getAllProductComments(productId, pageable),
                pagedResourcesAssembler,
                productId
        );
    }


    @PostMapping("/{productId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentationModel<ProductCommentModel> addProductComment(HttpServletResponse response,
                                                                      @PathVariable("productId") Long productId,
                                                                      @RequestBody ProductCommentModel comment) {
        RepresentationModel<ProductCommentModel> newProductComment = assembler.toModel(productCommentService.addProductComment(productId, comment));
        response.addHeader(HttpHeaders.LOCATION, newProductComment.getLink("self").get().getHref());
        return newProductComment;
    }


    @GetMapping("/{productId}/comments/{commentId}")
    public RepresentationModel<ProductCommentModel> getProductComment(@PathVariable("productId") Long productId,
                                                                      @PathVariable("commentId") Long commentId) {
        return assembler.toModel(productCommentService.getProductComment(productId, commentId));
    }

    @PutMapping("/{productId}/comments/{commentId}")
    public RepresentationModel<ProductCommentModel> modifyProductComment(@PathVariable("productId") Long productId,
                                                                         @PathVariable("commentId") Long commentId,
                                                                         @RequestBody ProductCommentModel comment) {
        return assembler.toModel(productCommentService.modifyProductComment(productId, commentId, comment));
    }

    @DeleteMapping("/{productId}/comments/{commentId}")
    public RepresentationModel<ProductCommentModel> deleteProductComment(@PathVariable("productId") Long productId,
                                                                         @PathVariable("commentId") Long commentId) {
        return assembler.toModel(productCommentService.deleteProductComment(productId, commentId));
    }

}
