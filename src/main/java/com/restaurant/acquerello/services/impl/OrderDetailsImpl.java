package com.restaurant.acquerello.services.impl;

import com.restaurant.acquerello.models.OrderDetails;
import com.restaurant.acquerello.repositories.OrderDetailsRepository;
import com.restaurant.acquerello.services.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsImpl implements OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<OrderDetails> getAll() {
        return orderDetailsRepository.findAll();
    }

    @Override
    public Optional<OrderDetails> getById(Long id) {
        return orderDetailsRepository.findById(id);
    }

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    public List<OrderDetails> listAll() {
        return null;
    }

    @Override
    public void deleteOrderDetails(OrderDetails orderDetails) {
        orderDetailsRepository.delete(orderDetails);
    }
}
