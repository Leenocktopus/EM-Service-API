package com.leandoer.assembler;



import com.leandoer.controller.CategoryController;
import com.leandoer.controller.MainController;
import com.leandoer.entity.model.CategoryModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class CategoryAssembler implements RepresentationModelAssembler<CategoryModel, RepresentationModel<CategoryModel>> {
    @Override
    public RepresentationModel<CategoryModel> toModel(CategoryModel category) {
        category.add(
                linkTo(methodOn(CategoryController.class).getOneCategory(category.getId())).withSelfRel(),
                linkTo(methodOn(CategoryController.class).getAllCategories()).withRel("categories")
        );
        return category;
    }

    @Override
    public CollectionModel<RepresentationModel<CategoryModel>> toCollectionModel(Iterable<? extends CategoryModel> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities).add(
                linkTo(methodOn(MainController.class).root()).withRel("root")
        );
    }
}
