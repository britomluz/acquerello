package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderTypeController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("api/order")
    public List<OrderTypeDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(OrderTypeDTO::new).collect(Collectors.toList());
    }
}
