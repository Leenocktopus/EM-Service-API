package com.leandoer.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1")
public class MainController {

    @GetMapping
    public ResponseEntity<BaseModel> root(){
        BaseModel model = new BaseModel("Welcome to e-commerce API");
        model.add(
                linkTo(methodOn(MainController.class).root()).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts(null, new PagedResourcesAssembler<>(null, null))).withRel("products"),
                linkTo(methodOn(CategoryController.class).getAllCategories()).withRel("categories"),
                linkTo(methodOn(ManufacturerController.class).getAllManufacturers()).withRel("manufacturers"),
                linkTo(methodOn(OrderController.class).getAllOrders(null, null)).withRel("orders")
        );
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class BaseModel extends RepresentationModel<BaseModel>{
        String title;

    }
}
