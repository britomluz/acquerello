package com.restaurant.acquerello.services;

//CREATED BY GABRIEL

import com.restaurant.acquerello.models.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryServices {
    public ProductCategory save(ProductCategory productCategory);
    public List<ProductCategory> getAll();
    public void deleteProductCategory(Long id);
    public Optional<ProductCategory> getById(Long id);
}
