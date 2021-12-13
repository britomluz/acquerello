package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.CompleteRegisterDTO;
import com.restaurant.acquerello.models.*;
import com.restaurant.acquerello.repositories.OrderDetailsRepository;
import com.restaurant.acquerello.services.AddressService;
import com.restaurant.acquerello.services.OrderService;
import com.restaurant.acquerello.services.ProductService;
import com.restaurant.acquerello.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

// Created by Brian

@RestController
@RequestMapping("/api")
public class NoAuthUserController {

    @Autowired
    public UserService userService;

    @Autowired
    public AddressService addressService;

    @Autowired
    public OrderService orderService;

    @Autowired
    public OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public ProductService productService;

    @PostMapping("/order/checkout")
    public ResponseEntity<?> createUser(@RequestBody CompleteRegisterDTO completeRegisterDTO) {
        // check empty field for user

        if(completeRegisterDTO.getFirstName().isEmpty()) {
            return new ResponseEntity<>("First Name field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getLastName().isEmpty()) {
            return new ResponseEntity<>("Last Name field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getEmail().isEmpty()) {
            return new ResponseEntity<>("Email field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("Password field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getNumber() == null) {
            return new ResponseEntity<>("Phone field cannot are empty", HttpStatus.FORBIDDEN);
        }

        // check empty fields for address

        if(completeRegisterDTO.getStreet().isEmpty()) {
            return new ResponseEntity<>("Street field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getNumberStreet() == null) {
            return new ResponseEntity<>("Nº Street field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getZip().isEmpty()) {
            return new ResponseEntity<>("Zip field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getState().isEmpty()) {
            return new ResponseEntity<>("State field cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getReference().isEmpty()) {
            return new ResponseEntity<>("Reference field cannot are empty", HttpStatus.FORBIDDEN);
        }

        // check order fields

        if(completeRegisterDTO.getTotal() == null || completeRegisterDTO.getTotal() < 1) {
            return new ResponseEntity<>("Fields cannot are empty", HttpStatus.FORBIDDEN);
        }

        // create user order and address

        User user = new User(completeRegisterDTO.getFirstName(), completeRegisterDTO.getLastName(), completeRegisterDTO.getEmail(), completeRegisterDTO.getPassword(), completeRegisterDTO.getNumber(), UserType.USER, "https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgUser_sps9k8.jpg");
        Address address = new Address(completeRegisterDTO.getStreet(), completeRegisterDTO.getNumberStreet(), completeRegisterDTO.getZip(), completeRegisterDTO.getState(), completeRegisterDTO.getReference());
        Order order = new Order(LocalDateTime.now(), LocalDateTime.now(), OrderState.PENDING, completeRegisterDTO.getTotal(), completeRegisterDTO.getType());

        // add address and order to user
        user.addAddress(address);
        user.addOrder(order);

        userService.save(user);
        addressService.save(address);
        orderService.save(order);

        // Brian: keep "for" on this place, or get server error

        for (Product product : completeRegisterDTO.getProducts()){
            Integer quantity = product.getQuantity();
            Long idProduct = product.getId();
            Product product1 = productService.getById(idProduct).orElse(null);
            assert product1 != null;
            OrderDetails orderDetails = new OrderDetails(quantity,product1,order);
            product1.addOrderDetail(orderDetails);
            order.addOrderDetail(orderDetails);
            address.addOrderDetails(orderDetails);
            orderDetailsRepository.save(orderDetails);
        }


        return new ResponseEntity<>("Checkout and user created", HttpStatus.CREATED);
    }
}
