package com.leandoer.service.implementation;

import com.leandoer.entity.Category;
import com.leandoer.entity.model.CategoryModel;
import com.leandoer.exception.EntityConflictException;
import com.leandoer.repository.CategoryRepository;
import com.leandoer.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<CategoryModel> getAllCategories() {
        List<CategoryModel> result = categoryRepository.findAll().stream()
                .map(CategoryModel::new)
                .collect(Collectors.toList());
        return result;
    }


    @Override
    public CategoryModel addCategory(CategoryModel category) {
        checkForDuplicate(category);
        categoryRepository.save(category.toCategory());
        return new CategoryModel(categoryRepository.findByName(category.getName()).orElseThrow(RuntimeException::new));
    }

    @Override
    public CategoryModel getOneCategory(long id) {
        return new CategoryModel(categoryRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    public CategoryModel modifyCategory(long id, CategoryModel category) {
        checkForDuplicate(category);
        Category selected = categoryRepository.findById(id).orElse(new Category());
        selected.setName(category.getName());
        categoryRepository.save(selected);
        return new CategoryModel(selected);
    }

    @Override
    public CategoryModel deleteCategory(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(RuntimeException::new);
        categoryRepository.delete(category);
        return new CategoryModel(category);
    }

    private void checkForDuplicate(CategoryModel category) {
        String name = category.getName();
        if (categoryRepository.existsByName(name)) {
            throw new EntityConflictException("Category with name \" " + name + "\" already exists");
        }
    }
}
