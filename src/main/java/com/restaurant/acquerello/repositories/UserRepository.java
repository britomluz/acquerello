package com.restaurant.acquerello.repositories;

import com.restaurant.acquerello.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//CREATED BY BRIAN

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
