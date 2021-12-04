package com.restaurant.acquerello.repositories;

import com.restaurant.acquerello.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//CREATED BY GABRIEL

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {
}
