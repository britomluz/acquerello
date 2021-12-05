package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.User;

//CREATED BY BRIAN

public interface UserServices  {
    public User save(User user);
    public User getByEmail(String email);
}
