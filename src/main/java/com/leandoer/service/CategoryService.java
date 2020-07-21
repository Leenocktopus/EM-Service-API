package com.leandoer.service;

import com.leandoer.entity.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto addCategory(CategoryDto category);

    CategoryDto getOneCategory(long id);

    CategoryDto modifyCategory(long id, CategoryDto category);

    CategoryDto deleteCategory(long id);
}
