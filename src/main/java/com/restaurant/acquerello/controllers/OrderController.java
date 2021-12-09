package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.OrderDTO;
import com.restaurant.acquerello.dtos.OrderDetailsDTO;
import com.restaurant.acquerello.dtos.OrderTypeDTO;
import com.restaurant.acquerello.models.*;
import com.restaurant.acquerello.repositories.OrderDetailsRepository;
import com.restaurant.acquerello.repositories.OrderRepository;
import com.restaurant.acquerello.repositories.UserRepository;
import com.restaurant.acquerello.services.OrderService;
import com.restaurant.acquerello.services.UserService;
import com.restaurant.acquerello.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private UserService userServices;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderServiceImpl orderService;


    @GetMapping("/order")
    public List<OrderTypeDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(OrderTypeDTO::new).collect(Collectors.toList());
    }


    @GetMapping("/user/current/orders/{id}")
    public ResponseEntity<Object> getOrders(Authentication authentication, @PathVariable Long id){

        User user = userServices.getByEmail(authentication.getName());
        Order order = orderService.getById(id).orElse(null);

        if(!user.getOrders().contains(order)){
            return new ResponseEntity<>("Order incorrect",HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(orderService.getById(id).map(OrderTypeDTO::new).orElse(null), HttpStatus.CREATED);
    }


    @GetMapping("/order/details")
    public List<OrderDetailsDTO> getAllOrderDetails() {
        return orderDetailsRepository.findAll().stream().map(OrderDetailsDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/order/buy")
    public ResponseEntity<?> editOrder(Authentication authentication, @RequestBody OrderDTO orderDTO) {

        User user = userServices.getByEmail(authentication.getName());

        if(orderDTO.getName() == null || orderDTO.getName().isEmpty()) {
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

    @GetMapping("/order/confirm/{id}")
    public ResponseEntity<?> confirmOrder(Authentication authentication, @PathVariable Long id) {

        User user = userServices.getByEmail(authentication.getName());

        if(user.getType().equals(UserType.USER)) {
            return new ResponseEntity<>("Access denied",HttpStatus.FORBIDDEN);
        }

        // search the order by id

        Order order = orderRepository.findOrderById(id);
        order.setAceptedDate(LocalDateTime.now());

        orderRepository.save(order);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/order/cancel/{id}")
    public ResponseEntity<?> cancelOrder(Authentication authentication, @PathVariable Long id) {

        User user = userServices.getByEmail(authentication.getName());

        // delete the order by id
        orderRepository.deleteById(id);

        return new ResponseEntity<>("Order deleted",HttpStatus.ACCEPTED);
    }

    @PatchMapping("/order/edit/{id}")
    public ResponseEntity<?> editOrder(Authentication authentication, @RequestParam OrderState orderState, @PathVariable Long id) {

        User user = userServices.getByEmail(authentication.getName());

        if(user.getType().equals(UserType.USER)) {
            return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
        }

        Order order = orderRepository.findOrderById(id);

        if(order.getState().equals(orderState)) {
            return new ResponseEntity<>("Cannot change by the same state", HttpStatus.FORBIDDEN);
        }

        order.setState(orderState);

        orderRepository.save(order);

        return new ResponseEntity<>("Order edited", HttpStatus.ACCEPTED);
    }
}
