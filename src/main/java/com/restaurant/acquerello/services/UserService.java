package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();
    public User getByEmail(String email);
    public void save(User user);
}
