package com.restaurant.acquerello.services.impl;

import com.restaurant.acquerello.models.Product;
import com.restaurant.acquerello.repositories.ProductRepository;
import com.restaurant.acquerello.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//CREATED BY GABRIEL

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long id) {
    productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }
}
