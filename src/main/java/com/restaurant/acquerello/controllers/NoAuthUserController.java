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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    public PasswordEncoder passwordEncoder;

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

        // check order fields

        if(completeRegisterDTO.getTotal() == null || completeRegisterDTO.getTotal() < 1) {
            return new ResponseEntity<>("Fields cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(completeRegisterDTO.getType().equals(OrderType.LOCAL)){
            if(completeRegisterDTO.getTableNumber() < 1 || completeRegisterDTO.getTableNumber() == null){
                return new ResponseEntity<>("Please, write the table number", HttpStatus.FORBIDDEN);

            }
        }

        // create user order and address

        User user = new User(completeRegisterDTO.getFirstName(), completeRegisterDTO.getLastName(), completeRegisterDTO.getEmail(), passwordEncoder.encode(completeRegisterDTO.getPassword()), completeRegisterDTO.getNumber(), UserType.USER, "https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgUser_sps9k8.jpg");
        Order order = new Order(LocalDateTime.now(), LocalDateTime.now(), OrderState.PENDING, completeRegisterDTO.getTotal(), completeRegisterDTO.getType(), completeRegisterDTO.getTableNumber());

        // add address and order to user

        user.addOrder(order);

        // save repository
        userService.save(user);
        orderService.save(order);

        // check empty fields for address

        if(completeRegisterDTO.getStreet().length() > 0 && completeRegisterDTO.getNumberStreet() != null && completeRegisterDTO.getZip().length() > 0 && completeRegisterDTO.getState().length() > 0 && completeRegisterDTO.getReference().length() > 0) {
            Address address = new Address(completeRegisterDTO.getStreet(), completeRegisterDTO.getNumberStreet(), completeRegisterDTO.getZip(), completeRegisterDTO.getState(), completeRegisterDTO.getReference());
            user.addAddress(address);
            System.out.println(address);
            addressService.save(address);
            orderService.save(order);

            for (Product product : completeRegisterDTO.getProducts()){
                Integer quantity = product.getQuantity();
                Long idProduct = product.getId();
                Product product1 = productService.getById(idProduct).orElse(null);
                assert product1 != null;
                OrderDetails orderDetails = new OrderDetails(quantity,product1,order);
                order.addAddress(address);
                orderDetailsRepository.save(orderDetails);
            }

            return new ResponseEntity<>("Order No."+order.getId(), HttpStatus.CREATED);
        }

        // Brian: keep "for" on this place, or get server error

        for (Product product : completeRegisterDTO.getProducts()){
            Integer quantity = product.getQuantity();
            Long idProduct = product.getId();
            Product product1 = productService.getById(idProduct).orElse(null);
            assert product1 != null;
            OrderDetails orderDetails = new OrderDetails(quantity,product1,order);
            orderDetailsRepository.save(orderDetails);
        }



        return new ResponseEntity<>("Order No." +order.getId(), HttpStatus.CREATED);
    }
}
