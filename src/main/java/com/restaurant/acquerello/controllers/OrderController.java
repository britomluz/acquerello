package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.OrderDTO;
import com.restaurant.acquerello.dtos.OrderDetailsDTO;
import com.restaurant.acquerello.dtos.OrderTypeDTO;
import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.OrderDetails;
import com.restaurant.acquerello.models.OrderState;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.repositories.OrderDetailsRepository;
import com.restaurant.acquerello.repositories.OrderRepository;
import com.restaurant.acquerello.repositories.UserRepository;
import com.restaurant.acquerello.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userServices;

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

    @PostMapping("/api/order/buy")
    public ResponseEntity<?> editOrder(Authentication authentication, @RequestBody OrderDTO orderDTO) {

        User user = userServices.getByEmail(authentication.getName());

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

        OrderDetails orderDetails = new OrderDetails(orderDTO.getName(), orderDTO.getQuantity(), orderDTO.getPrice(), orderDTO.getTotal());
        Order order = new Order(LocalDateTime.now(), LocalDateTime.now(), OrderState.PENDING, orderDTO.getTotal());

        orderDetails.addOrders(order);

        user.addOrder(order);

        orderDetailsRepository.save(orderDetails);
        orderRepository.save(order);


        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/order/confirm/{id}")
    public ResponseEntity<?> confirmOrder(Authentication authentication, @PathVariable Long id) {

        User user = userServices.getByEmail(authentication.getName());

        if(user.getType().equals("USER")) {
            return new ResponseEntity<>("Access denied",HttpStatus.FORBIDDEN);
        }

        // search the order by id

        Order order = orderRepository.findOrderById(id);
        order.setAceptedDate(LocalDateTime.now());

        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/api/order/cancel/{id}")
    public ResponseEntity<?> cancelOrder(Authentication authentication, @PathVariable Long id) {

        User user = userServices.getByEmail(authentication.getName());

        // delete the order by id

        orderRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("/api/order/edit/{id}")
    public ResponseEntity<?> editOrder(Authentication authentication, @RequestParam OrderState orderState, @PathVariable Long id) {

        User user = userServices.getByEmail(authentication.getName());

        if(user.getType().equals("USER")) {
            return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
        }

        Order order = orderRepository.findOrderById(id);

        order.setState(orderState);

        orderRepository.save(order);

        return new ResponseEntity<>("Order edited", HttpStatus.ACCEPTED);
    }
}
