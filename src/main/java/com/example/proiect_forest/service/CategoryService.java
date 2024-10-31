package com.example.proiect_forest.service;

import com.example.proiect_forest.model.Category;

import java.util.List;

public interface CategoryService {
    void saveCategory(Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    void updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}
