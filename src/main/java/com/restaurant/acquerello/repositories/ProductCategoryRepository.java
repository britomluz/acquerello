package com.restaurant.acquerello.repositories;

import com.restaurant.acquerello.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//CREATED BY GABRIEL

@RepositoryRestResource
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
