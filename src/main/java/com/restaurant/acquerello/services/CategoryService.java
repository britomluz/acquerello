package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Category;

import java.util.List;
import java.util.Optional;

//CREATED BY GABRIEL

public interface CategoryService {
    public Category save(Category category);
    public List<Category> getAll();
    public void deleteCategory(Long id);
    public Optional<Category> getById(Long id);
}
