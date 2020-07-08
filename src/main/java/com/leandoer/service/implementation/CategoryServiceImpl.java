package com.leandoer.service.implementation;

import com.leandoer.entity.Category;
import com.leandoer.repository.CategoryRepository;
import com.leandoer.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> addCategory(Category category) {
        if (categoryRepository.existsById(category)) {
            throw new RuntimeException("category");
        }
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }
}
