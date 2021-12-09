package com.restaurant.acquerello.services.impl;

import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.repositories.OrderRepository;
import com.restaurant.acquerello.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> listAll() {
        return orderRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }
}
