package com.leandoer.assembler;

import com.leandoer.controller.ImageController;
import com.leandoer.entity.model.ImageModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Iterator;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ImageAssembler implements RepresentationModelAssembler<ImageModel, RepresentationModel<ImageModel>> {
    @Override
    public RepresentationModel<ImageModel> toModel(ImageModel image) {
        return image.add(
                linkTo(methodOn(ImageController.class).getImage(image.getProductId(), image.getId())).withSelfRel()
        );
    }

    @Override
    public CollectionModel<RepresentationModel<ImageModel>> toCollectionModel(Iterable<? extends ImageModel> entities) {
        CollectionModel<RepresentationModel<ImageModel>> result = RepresentationModelAssembler.super.toCollectionModel(entities);
        Iterator<? extends ImageModel> iterator = entities.iterator();
        if (iterator.hasNext()) {
            result.add(
                    linkTo(methodOn(ImageController.class).getAllImages(iterator.next().getProductId())).withSelfRel()
            );
        }
        return result;
    }
}
