package com.leandoer.service;

import com.leandoer.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    List<Category> addCategory(Category category);
}
