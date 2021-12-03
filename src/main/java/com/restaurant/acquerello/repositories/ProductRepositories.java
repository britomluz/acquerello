package com.restaurant.acquerello.repositories;

import com.restaurant.acquerello.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepositories extends JpaRepository<Product, Long> {
}
