package com.restaurant.acquerello.repositories;

import com.restaurant.acquerello.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CategoryRepositories extends JpaRepository<Category,Long> {
}
