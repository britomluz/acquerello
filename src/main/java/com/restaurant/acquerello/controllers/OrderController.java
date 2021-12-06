package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.OrderDTO;
import com.restaurant.acquerello.dtos.OrderDetailsDTO;
import com.restaurant.acquerello.dtos.OrderTypeDTO;
import com.restaurant.acquerello.repositories.OrderDetailsRepository;
import com.restaurant.acquerello.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PatchMapping("/api/order/edit")
    public ResponseEntity<?> editOrder(Authentication authentication, @RequestBody OrderDTO orderDTO) {

        if(orderDTO.getName() == null || orderDTO.getName() == "") {
            return new ResponseEntity<>("Fields cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(orderDTO.getQuantity() == null || orderDTO.getQuantity() < 1) {
            return new ResponseEntity<>("Fields cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(orderDTO.getPrice() == null || orderDTO.getPrice() < 1) {
            return new ResponseEntity<>("Fields cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(orderDTO.getTotal() == null || orderDTO.getTotal() < 1) {
            return new ResponseEntity<>("Fields cannot are empty", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
