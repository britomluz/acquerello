package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.OrderDetailsDTO;
import com.restaurant.acquerello.dtos.OrderTypeDTO;
import com.restaurant.acquerello.repositories.OrderDetailsRepository;
import com.restaurant.acquerello.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @GetMapping("/api/order")
    public List<OrderTypeDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(OrderTypeDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/api/order/details")
    public List<OrderDetailsDTO> getAllOrderDetails() {
        return orderDetailsRepository.findAll().stream().map(OrderDetailsDTO::new).collect(Collectors.toList());
    }
}
