package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Booking;
import com.restaurant.acquerello.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> getAll();
    public User getByEmail(String email);
    public void save(User user);
    public Optional<User> getById(Long id);
}
