package com.leandoer.assembler;

import com.leandoer.controller.ProductAttributeController;
import com.leandoer.controller.ProductController;
import com.leandoer.entity.model.ProductAttributeModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductAttributeAssembler implements RepresentationModelAssembler<ProductAttributeModel, RepresentationModel<ProductAttributeModel>> {


    @Override
    public RepresentationModel<ProductAttributeModel> toModel(ProductAttributeModel attribute) {
        return attribute.add(
                linkTo(methodOn(ProductAttributeController.class).getProductAttribute(attribute.getProductId(),attribute.getId())).withSelfRel(),
                linkTo(methodOn(ProductAttributeController.class).getAllProductAttributes(attribute.getProductId())).withRel("attributes")
        );
    }


    public CollectionModel<RepresentationModel<ProductAttributeModel>> toCollectionModel(Iterable<? extends ProductAttributeModel> attributes, long productId) {
        return RepresentationModelAssembler.super.toCollectionModel(attributes)
                .add(
                        linkTo(methodOn(ProductController.class).getProduct(productId)).withRel("product")
                );
    }
}
