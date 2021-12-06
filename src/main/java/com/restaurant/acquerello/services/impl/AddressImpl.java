package com.restaurant.acquerello.services.impl;

import com.restaurant.acquerello.models.Address;
import com.restaurant.acquerello.repositories.AddressRepository;
import com.restaurant.acquerello.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Created by Brian

@Service
public class AddressImpl implements AddressService {
    @Autowired
    public AddressRepository addressRepository;

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> getById(Long id) {
        return addressRepository.findById(id);
    }
}
