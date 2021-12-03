package com.restaurant.acquerello.services.impl;

import com.restaurant.acquerello.models.Product;
import com.restaurant.acquerello.repositories.ProductRepositories;
import com.restaurant.acquerello.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepositories productRepositories;

    @Override
    public Product save(Product product) {
        return productRepositories.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepositories.findAll();
    }

    @Override
    public void deleteProduct(Long id) {
    productRepositories.deleteById(id);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepositories.findById(id);
    }
}
