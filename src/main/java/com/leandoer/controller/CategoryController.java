package com.leandoer.controller;

import com.leandoer.entity.Category;
import com.leandoer.entity.dto.CategoryDto;
import com.leandoer.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody CategoryDto category) {
        return categoryService.addCategory(category);
    }

    @GetMapping("/{id}")
    public CategoryDto getOneCategory(@PathVariable("id") long id){
        return categoryService.getOneCategory(id);
    }

    @PutMapping("/{id}")
    public CategoryDto modifyCategory(@PathVariable("id") long id, @RequestBody CategoryDto category) {
        return categoryService.modifyCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public CategoryDto deleteCategory(@PathVariable("id") long id) {
        return categoryService.deleteCategory(id);
    }


}
