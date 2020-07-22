package com.leandoer.service;

import com.leandoer.entity.model.CategoryModel;

import java.util.List;

public interface CategoryService {
    List<CategoryModel> getAllCategories();

    CategoryModel addCategory(CategoryModel category);

    CategoryModel getOneCategory(long id);

    CategoryModel modifyCategory(long id, CategoryModel category);

    CategoryModel deleteCategory(long id);
}
