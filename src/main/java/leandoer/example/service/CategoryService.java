package leandoer.example.service;

import leandoer.example.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    List<Category> addCategory(Category category);
}
