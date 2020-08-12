package com.leandoer.assembler;

import com.leandoer.controller.MainController;
import com.leandoer.controller.ProductAttributeController;
import com.leandoer.controller.ProductCommentController;
import com.leandoer.controller.ProductController;
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
                linkTo(methodOn(ProductAttributeController.class).getAllProductAttributes(product.getId())).withRel("attributes"),
                linkTo(methodOn(ProductCommentController.class).getAllProductComments(product.getId(), null, new PagedResourcesAssembler<>(null, null))).withRel("comments")
        );
        return product;
    }

    @Override
    public CollectionModel<RepresentationModel<ProductModel>> toCollectionModel(Iterable<? extends ProductModel> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities).add(
                linkTo(methodOn(MainController.class).root()).withRel("root")
        );
    }


    public CollectionModel<RepresentationModel<ProductModel>> toCollectionModel(Page<ProductModel> products,
                                                                           PagedResourcesAssembler<ProductModel> pagedResourcesAssembler) {
        return pagedResourcesAssembler.toModel(products, this).add(
                linkTo(methodOn(MainController.class).root()).withRel("root")
        );
    }
}
