package com.restaurant.acquerello.repositories;

import com.restaurant.acquerello.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
