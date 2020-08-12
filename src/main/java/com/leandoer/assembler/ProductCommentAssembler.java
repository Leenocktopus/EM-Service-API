package com.leandoer.assembler;

import com.leandoer.controller.ProductCommentController;
import com.leandoer.controller.ProductController;
import com.leandoer.entity.model.ProductCommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductCommentAssembler implements RepresentationModelAssembler<ProductCommentModel, RepresentationModel<ProductCommentModel>> {
    @Override
    public RepresentationModel<ProductCommentModel> toModel(ProductCommentModel comment) {
        return comment.add(
                linkTo(methodOn(ProductCommentController.class).getProductComment(comment.getProductId(), comment.getId())).withSelfRel()
        );
    }



    public CollectionModel<RepresentationModel<ProductCommentModel>> toCollectionModel(Page<ProductCommentModel> comments,
                                                                                       PagedResourcesAssembler<ProductCommentModel> pagedResourcesAssembler,
                                                                                       long productId) {

        return pagedResourcesAssembler.toModel(comments, this).add(
                linkTo(methodOn(ProductController.class).getProduct(productId)).withRel("product")

        );


    }
}
