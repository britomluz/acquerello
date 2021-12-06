package com.restaurant.acquerello.controllers;

//CREATED BY BRIAN

import com.restaurant.acquerello.dtos.UserDTO;
import com.restaurant.acquerello.repositories.UserRepository;
import com.restaurant.acquerello.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/api/users")
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

}
