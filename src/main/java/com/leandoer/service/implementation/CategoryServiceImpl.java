package com.leandoer.service.implementation;

import com.leandoer.entity.Category;
import com.leandoer.entity.dto.CategoryDto;
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
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> result = categoryRepository.findAll().stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());
        return result;
    }


    @Override
    public CategoryDto addCategory(CategoryDto category) {
        checkForDuplicate(category);
        categoryRepository.save(category.toCategory());
        return new CategoryDto(categoryRepository.findByName(category.getName()).orElseThrow(RuntimeException::new));
    }

    @Override
    public CategoryDto getOneCategory(long id) {
        return new CategoryDto(categoryRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    public CategoryDto modifyCategory(long id, CategoryDto category) {
        checkForDuplicate(category);
        Category selected = categoryRepository.findById(id).orElse(new Category());
        selected.setName(category.getName());
        categoryRepository.save(selected);
        return new CategoryDto(selected);
    }

    @Override
    public CategoryDto deleteCategory(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(RuntimeException::new);
        categoryRepository.delete(category);
        return new CategoryDto(category);
    }

    private void checkForDuplicate(CategoryDto category) {
        String name = category.getName();
        if (categoryRepository.existsByName(name)) {
            throw new EntityConflictException("Category with name \" " + name + "\" already exists");
        }
    }
}
