package com.restaurant.acquerello.services.impl;


import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.repositories.UserRepository;
import com.restaurant.acquerello.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//CREATED BY BRIAN

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
      return userRepository.save(user);
    }
}
