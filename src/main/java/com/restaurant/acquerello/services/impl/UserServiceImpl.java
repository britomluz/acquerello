package com.restaurant.acquerello.services.impl;


import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.repositories.UserRepository;
import com.restaurant.acquerello.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//CREATED BY BRIAN

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
      return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
