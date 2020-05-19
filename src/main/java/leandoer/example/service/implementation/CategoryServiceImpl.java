package leandoer.example.service.implementation;

import leandoer.example.entity.Category;
import leandoer.example.exception.DuplicateSaveException;
import leandoer.example.repository.CategoryRepository;
import leandoer.example.service.CategoryService;
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
            throw new DuplicateSaveException("category");
        }
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }
}
