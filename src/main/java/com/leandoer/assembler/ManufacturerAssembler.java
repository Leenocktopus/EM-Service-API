package com.leandoer.assembler;

import com.leandoer.controller.MainController;
import com.leandoer.controller.ManufacturerController;
import com.leandoer.entity.model.ManufacturerModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ManufacturerAssembler implements RepresentationModelAssembler<ManufacturerModel, RepresentationModel<ManufacturerModel>> {
    @Override
    public RepresentationModel<ManufacturerModel> toModel(ManufacturerModel manufacturer) {
        manufacturer.add(
                linkTo(methodOn(ManufacturerController.class).getOneManufacturer(manufacturer.getId())).withSelfRel(),
                linkTo(methodOn(ManufacturerController.class).getAllManufacturers()).withRel("manufacturers")
        );
        return manufacturer;
    }

    @Override
    public CollectionModel<RepresentationModel<ManufacturerModel>> toCollectionModel(Iterable<? extends ManufacturerModel> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities).add(
                linkTo(methodOn(MainController.class).root()).withRel("root")
        );
    }
}
