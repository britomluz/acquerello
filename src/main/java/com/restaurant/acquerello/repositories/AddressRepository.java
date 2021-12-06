package com.restaurant.acquerello.repositories;

import com.restaurant.acquerello.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Created by Brian

@RepositoryRestResource
public interface AddressRepository extends JpaRepository<Address, Long> {
}
