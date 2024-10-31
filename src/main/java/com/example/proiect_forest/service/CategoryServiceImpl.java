package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Category;
import com.example.proiect_forest.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void saveCategory(Category category) {
         categoryRepository.save(category);
    }

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    @Transactional
    @Override
    public void updateCategory(Long id, Category category) {
        if (categoryRepository.existsById(id)) {
            category.setCategoryId(id);
             categoryRepository.save(category);
        }

    }
    @Transactional
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
