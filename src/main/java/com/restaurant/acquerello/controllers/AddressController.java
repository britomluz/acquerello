package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.AddressCreateDTO;
import com.restaurant.acquerello.models.Address;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.models.UserType;
import com.restaurant.acquerello.repositories.UserRepository;
import com.restaurant.acquerello.services.AddressService;
import com.restaurant.acquerello.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// Created by Brian

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    public UserService userService;

    @Autowired
    public AddressService addressService;

    @RequestMapping(value = "/create/address", method = RequestMethod.POST)
    public ResponseEntity<?> createAddress(Authentication authentication, @RequestBody AddressCreateDTO addressCreateDTO) {

        User user = userService.getByEmail(authentication.getName());

        if(user.getType().equals(UserType.ADMIN)) {
            return new ResponseEntity<>("The admin cannot create a address for the user", HttpStatus.FORBIDDEN);
        }

        Address address = new Address(addressCreateDTO.getStreet(), addressCreateDTO.getNumber(), addressCreateDTO.getZip(), addressCreateDTO.getState(), addressCreateDTO.getReference());
        user.addAddress(address);

        addressService.save(address);

        return new ResponseEntity<>("Address created", HttpStatus.CREATED);
    }

}
