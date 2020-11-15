package com.leandoer.assembler;

import com.leandoer.controller.*;
import com.leandoer.entity.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductAssembler implements RepresentationModelAssembler<ProductModel, RepresentationModel<ProductModel>> {
    @Override
    public RepresentationModel<ProductModel> toModel(ProductModel product) {
        product.add(
                linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts(null, null, null, null, null)).withRel("products").expand(),
                linkTo(methodOn(ImageController.class).getAllImages(product.getId())).withRel("images"),
                linkTo(methodOn(ProductAttributeController.class).getAllProductAttributes(product.getId())).withRel("attributes"),
                linkTo(methodOn(ProductCommentController.class).getAllProductComments(product.getId(), null, new PagedResourcesAssembler<>(null, null))).withRel("comments")
        );
        return product;
    }


    public CollectionModel<RepresentationModel<ProductModel>> toCollectionModel(Page<ProductModel> products,
                                                                           PagedResourcesAssembler<ProductModel> pagedResourcesAssembler) {
        return pagedResourcesAssembler.toModel(products, this).add(
                linkTo(methodOn(MainController.class).root()).withRel("root")
        );
    }
}
