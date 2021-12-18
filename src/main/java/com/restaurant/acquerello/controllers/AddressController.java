package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.AddressCreateDTO;
import com.restaurant.acquerello.dtos.AddressDTO;
import com.restaurant.acquerello.models.Address;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.models.UserType;
import com.restaurant.acquerello.repositories.AddressRepository;
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
    public AddressRepository addressRepository;

    @Autowired
    public UserService userService;

    @Autowired
    public AddressService addressService;

    @RequestMapping("/address")
    public List<AddressDTO> getAll() {
        return addressService.getAll().stream().map(AddressDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/address/create", method = RequestMethod.POST)
    public ResponseEntity<?> createAddress(Authentication authentication, @RequestBody AddressCreateDTO addressCreateDTO) {

        User user = userService.getByEmail(authentication.getName());


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

        Address address = addressRepository.getById(id);

        /*List<Address> verify = user.getAddress().stream().filter(add -> add.getId().equals(id)).collect(Collectors.toList());

        if (verify.size() < 1) {
            return new ResponseEntity<>("Invalid Address", HttpStatus.FORBIDDEN);
        }*/

        if(addressCreateDTO.getStreet() != null) {
            address.setStreet(addressCreateDTO.getStreet());
        }

        if(addressCreateDTO.getNumber() != null) {
            address.setNumber(addressCreateDTO.getNumber());
        }

        if(addressCreateDTO.getZip() != null) {
            address.setZip(addressCreateDTO.getZip());
        }

        if(addressCreateDTO.getState() != null) {
            address.setState(addressCreateDTO.getState());
        }

        if(addressCreateDTO.getReference() != null) {
            address.setReference(addressCreateDTO.getReference());
        }

        addressService.save(address);

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
