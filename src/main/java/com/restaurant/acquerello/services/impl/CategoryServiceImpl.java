package com.restaurant.acquerello.services.impl;

import com.restaurant.acquerello.models.Category;
import com.restaurant.acquerello.repositories.CategoryRepositories;
import com.restaurant.acquerello.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepositories categoryRepositories;

    @Override
    public Category save(Category category) {
        return categoryRepositories.save(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepositories.findAll();
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepositories.deleteById(id);
    }

    @Override
    public Optional<Category> getById(Long id) {
        return categoryRepositories.findById(id);
    }
}
