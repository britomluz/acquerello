package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public List<Order> getAll();
    public Optional<Order> getById(Long id);
    public Order save(Order order);
    public List<Order> listAll();
    public void deleteOrder(Order order);
}
