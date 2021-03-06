package com.restaurant.acquerello.repositories;

import com.restaurant.acquerello.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    Card getCardById(Long id);
}
