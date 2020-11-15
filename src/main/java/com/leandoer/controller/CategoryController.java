package com.leandoer.controller;

import com.leandoer.assembler.CategoryAssembler;
import com.leandoer.entity.model.CategoryModel;
import com.leandoer.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentationModel<CategoryModel> addCategory(HttpServletResponse response,
                                                          @RequestBody CategoryModel category) {
        RepresentationModel<CategoryModel> newCategory = assembler.toModel(categoryService.addCategory(category));
        response.addHeader(HttpHeaders.LOCATION, newCategory.getLink("self").get().getHref());
        return newCategory;
    }

    @GetMapping("/{id}")
    public RepresentationModel<CategoryModel> getOneCategory(@PathVariable("id") Long id) {
        return assembler.toModel(categoryService.getOneCategory(id));
    }

    @PutMapping("/{id}")
    public RepresentationModel<CategoryModel> modifyCategory(@PathVariable("id") Long id,
                                                             @RequestBody CategoryModel category) {
        return assembler.toModel(categoryService.modifyCategory(id, category));
    }

    @DeleteMapping("/{id}")
    public RepresentationModel<CategoryModel> deleteCategory(@PathVariable("id") Long id) {
        return assembler.toModel(categoryService.deleteCategory(id));
    }

}
