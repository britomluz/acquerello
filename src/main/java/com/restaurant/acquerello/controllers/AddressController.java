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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Created by Brian

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    public UserService userService;

    @Autowired
    public AddressService addressService;

    @RequestMapping(value = "/address/create", method = RequestMethod.POST)
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

    @RequestMapping(value = "/address/edit", method = RequestMethod.PATCH)
    public ResponseEntity<?> editAddress(Authentication authentication, @RequestParam Long id, @RequestBody AddressCreateDTO addressCreateDTO) {

        User user = userService.getByEmail(authentication.getName());

        if(user.getType().equals(UserType.ADMIN)) {
            return new ResponseEntity<>( "Admin cannot edit a personal address of a user",HttpStatus.FORBIDDEN);
        }

        List<Address> address = addressService.getById(id).stream().collect(Collectors.toList());

        if(addressCreateDTO.getStreet() != null) {
            address.get(0).setStreet(addressCreateDTO.getStreet());
        }

        if(addressCreateDTO.getNumber() != null) {
            address.get(0).setNumber(addressCreateDTO.getNumber());
        }

        if(addressCreateDTO.getZip() != null) {
            address.get(0).setZip(addressCreateDTO.getZip());
        }

        if(addressCreateDTO.getState() != null) {
            address.get(0).setZip(addressCreateDTO.getState());
        }

        if(addressCreateDTO.getReference() != null) {
            address.get(0).setZip(addressCreateDTO.getReference());
        }

        addressService.save(address.get(0));

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/address/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAddress(Authentication authentication, @RequestParam Long id) {

        User user = userService.getByEmail(authentication.getName());

        if(user.getType().equals(UserType.ADMIN)) {
            return new ResponseEntity<>("Only user can delete his address", HttpStatus.FORBIDDEN);
        }

        addressService.deleteById(id);

        return new ResponseEntity<>("Address deleted", HttpStatus.ACCEPTED);
    }

}
