package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Product save(Product product);
    public List<Product> getAll();
    public void deleteProduct(Long id);
    public Optional<Product> getById(Long id);
}
