package com.restaurant.acquerello.controllers;

//CREATED BY BRIAN

import com.restaurant.acquerello.dtos.CreateUserDTO;
import com.restaurant.acquerello.dtos.OrderDTO;
import com.restaurant.acquerello.dtos.UserDTO;
import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.models.UserType;
import com.restaurant.acquerello.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<UserDTO> getAll() {
        return userService.getAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @RequestMapping("/users/current")
    public UserDTO getUser(Authentication authentication) {
        return new UserDTO(userService.getByEmail(authentication.getName()));
    }

    @PostMapping("/users/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO user){
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()){
            return new ResponseEntity<>("Fields cannot be empty", HttpStatus.FORBIDDEN);
        }
        if (userService.getByEmail(user.getEmail()) != null){
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        User user1 = new User(user.getFirstName(), user.getLastName(), user.getEmail(), passwordEncoder.encode(user.getPassword()), user.getNumber(), UserType.USER, "https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgUser_sps9k8.jpg");
        userService.save(user1);
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

    @GetMapping("/admin/user/{id}")
    public ResponseEntity<Object> getAccounts(Authentication authentication, @PathVariable Long id){

        User user = userService.getByEmail(authentication.getName());

        if(!user.getType().equals(UserType.ADMIN)){
            return new ResponseEntity<>("Acces denied", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>( userService.getById(id).map(UserDTO::new).orElse(null), HttpStatus.CREATED);
    }

}
