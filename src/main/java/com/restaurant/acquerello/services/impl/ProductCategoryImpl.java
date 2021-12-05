package com.restaurant.acquerello.services.impl;

import com.restaurant.acquerello.models.ProductCategory;
import com.restaurant.acquerello.repositories.ProductCategoryRepository;
import com.restaurant.acquerello.services.ProductCategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

//CREATED BY GABRIEL

@Service
public class ProductCategoryImpl implements ProductCategoryServices {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public List<ProductCategory> getAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public void deleteProductCategory(Long id) {
        productCategoryRepository.deleteById(id);
    }

    @Override
    public Optional<ProductCategory> getById(Long id) {
        return productCategoryRepository.findById(id);
    }

    @Override
    public void deleteAll(Set<ProductCategory> categories) {
        productCategoryRepository.deleteAll(categories);
    }
}
