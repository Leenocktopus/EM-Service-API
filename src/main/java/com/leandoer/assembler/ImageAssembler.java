package com.leandoer.assembler;

import com.leandoer.controller.ImageController;
import com.leandoer.controller.ProductController;
import com.leandoer.entity.model.ImageModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ImageAssembler implements RepresentationModelAssembler<ImageModel, RepresentationModel<ImageModel>> {
    @Override
    public RepresentationModel<ImageModel> toModel(ImageModel image) {
        return image.add(
                linkTo(methodOn(ImageController.class).getImage(image.getProductId(), image.getId())).withSelfRel(),
                linkTo(methodOn(ImageController.class).getAllImages(image.getProductId())).withRel("images"),
                new Link(linkTo(DispatcherServlet.class).toString() + "/images/" + image.getProductId() + "/" + image.getFilename()).withRel("static")
        );
    }

    public CollectionModel<RepresentationModel<ImageModel>> toCollectionModel(Iterable<? extends ImageModel> images, Long id) {
        return RepresentationModelAssembler.super.toCollectionModel(images).add(
                linkTo(methodOn(ProductController.class).getProduct(id)).withRel("product")
        );
    }
}
