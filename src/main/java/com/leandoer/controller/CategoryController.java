package com.leandoer.controller;

import com.leandoer.assembler.CategoryAssembler;
import com.leandoer.entity.model.CategoryModel;
import com.leandoer.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    CategoryService categoryService;
    CategoryAssembler assembler;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryAssembler assembler) {
        this.categoryService = categoryService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<RepresentationModel<CategoryModel>> getAllCategories() {
        return assembler.toCollectionModel(categoryService.getAllCategories());
    }

    @PostMapping
    public RepresentationModel<CategoryModel> addCategory(@RequestBody CategoryModel category) {
        return assembler.toModel(categoryService.addCategory(category));
    }

    @GetMapping("/{id}")
    public RepresentationModel<CategoryModel> getOneCategory(@PathVariable("id") long id) {
        return assembler.toModel(categoryService.getOneCategory(id));
    }

    @PutMapping("/{id}")
    public RepresentationModel<CategoryModel> modifyCategory(@PathVariable("id") long id, @RequestBody CategoryModel category) {
        return assembler.toModel(categoryService.modifyCategory(id, category));
    }

    @DeleteMapping("/{id}")
    public RepresentationModel<CategoryModel> deleteCategory(@PathVariable("id") long id) {
        return assembler.toModel(categoryService.deleteCategory(id));
    }


}
