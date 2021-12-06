package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.User;

import java.util.List;

//CREATED BY BRIAN

public interface UserService {
    public User save(User user);
    public User getByEmail(String email);
    public List<User> getAll();
}
