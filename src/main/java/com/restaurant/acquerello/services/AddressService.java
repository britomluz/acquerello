package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Address;

import java.util.List;
import java.util.Optional;

// Created by Brian

public interface AddressService {
    public List<Address> getAll();
    public Address save(Address address);
    public Optional<Address> getById(Long id);
    public void deleteById(Long id);
}
