package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.CompleteRegisterDTO;
import com.restaurant.acquerello.models.Address;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.models.UserType;
import com.restaurant.acquerello.services.AddressService;
import com.restaurant.acquerello.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NoAuthUserController {

    @Autowired
    public UserService userService;

    @Autowired
    public AddressService addressService;

    @PostMapping("/order/checkout")
    public ResponseEntity<?> createUser(@RequestBody CompleteRegisterDTO completeRegisterDTO) {
        // check empty field for user

        if(completeRegisterDTO.getFirstName().isEmpty()) {
            return new ResponseEntity<>("First Name field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getLastName().isEmpty()) {
            return new ResponseEntity<>("Last Name field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getEmail().isEmpty()) {
            return new ResponseEntity<>("Email field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("Password field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getNumber() == null) {
            return new ResponseEntity<>("Phone field cannot are empty", HttpStatus.FORBIDDEN);
        }

        // check empty fields for address

        if(completeRegisterDTO.getStreet().isEmpty()) {
            return new ResponseEntity<>("Street field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getNumberStreet() == null) {
            return new ResponseEntity<>("Nº Street field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getZip().isEmpty()) {
            return new ResponseEntity<>("Zip field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getState().isEmpty()) {
            return new ResponseEntity<>("State field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getReference().isEmpty()) {
            return new ResponseEntity<>("Reference field cannot are empty", HttpStatus.FORBIDDEN);
        }

        // check order fields

        User user = new User(completeRegisterDTO.getFirstName(), completeRegisterDTO.getLastName(), completeRegisterDTO.getEmail(), completeRegisterDTO.getPassword(), completeRegisterDTO.getNumber(), UserType.USER, "https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgUser_sps9k8.jpg");
        Address address = new Address(completeRegisterDTO.getStreet(), completeRegisterDTO.getNumberStreet(), completeRegisterDTO.getZip(), completeRegisterDTO.getState(), completeRegisterDTO.getReference());

        user.addAddress(address);

        addressService.save(address);
        userService.save(user);

        return new ResponseEntity<>("Checkout and user created", HttpStatus.CREATED);
    }
}
