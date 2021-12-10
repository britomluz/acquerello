package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.OrderDTO;
import com.restaurant.acquerello.dtos.OrderToBuyDTO;
import com.restaurant.acquerello.dtos.OrderDetailsDTO;
import com.restaurant.acquerello.dtos.OrderTypeDTO;
import com.restaurant.acquerello.models.*;
import com.restaurant.acquerello.repositories.OrderDetailsRepository;
import com.restaurant.acquerello.repositories.OrderRepository;
import com.restaurant.acquerello.services.ProductService;
import com.restaurant.acquerello.services.UserService;
import com.restaurant.acquerello.services.impl.OrderDetailsImpl;
import com.restaurant.acquerello.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    private OrderDetailsImpl orderDetailsService;

    @Autowired
    private ProductService productService;


    @GetMapping("/order")
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(OrderDTO::new).collect(Collectors.toList());
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<Object> getOrders(Authentication authentication, @PathVariable Long id){

        User user = userServices.getByEmail(authentication.getName());
        Order order = orderService.getById(id).orElse(null);

        /*
        if(!user.getOrder().contains(order)){
            return new ResponseEntity<>("Order incorrect",HttpStatus.FORBIDDEN);
        }*/
        return new ResponseEntity<>(orderService.getById(id).map(OrderDTO::new).orElse(null), HttpStatus.CREATED);
    }
    @GetMapping("/order/current")
    public ResponseEntity<Object> getOrderUser(Authentication authentication){

        User user = userServices.getByEmail(authentication.getName());
        /*
        if(!user.getOrder().contains(order)){
            return new ResponseEntity<>("Order incorrect",HttpStatus.FORBIDDEN);
        }*/
        return new ResponseEntity<>( user.getOrders().stream().map(OrderDTO::new).collect(Collectors.toList()), HttpStatus.CREATED);
    }


    @GetMapping("/order/details")
    public List<OrderDetailsDTO> getAllOrderDetails() {
        return orderDetailsRepository.findAll().stream().map(OrderDetailsDTO::new).collect(Collectors.toList());
    }


    @GetMapping("/order/details/{id}")
    public List<OrderDetailsDTO> getAllOrderDetailsById() {
        return orderDetailsRepository.findAll().stream().map(OrderDetailsDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/order/buy")
    public ResponseEntity<?> editOrder(Authentication authentication, @RequestBody OrderToBuyDTO orderToBuyDTO) {

        User user = userServices.getByEmail(authentication.getName());
        if (user == null){
            return new ResponseEntity<>("User doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (!user.getType().equals(UserType.USER)){
            return new ResponseEntity<>("Don't have authority", HttpStatus.FORBIDDEN);
        }

        if(orderToBuyDTO.getTotal() == null || orderToBuyDTO.getTotal() < 1) {
            return new ResponseEntity<>("Fields cannot are empty", HttpStatus.FORBIDDEN);
        }

        Order order = new Order(LocalDateTime.now(), LocalDateTime.now(), OrderState.PENDING, orderToBuyDTO.getTotal());
        user.addOrder(order);
        orderRepository.save(order);

        for (Product product : orderToBuyDTO.getProducts()){
           Integer quantity = product.getQuantity();
           Long idProduct = product.getId();
           Product product1 = productService.getById(idProduct).orElse(null);
           assert product1 != null;
           OrderDetails orderDetails = new OrderDetails(quantity,product1,order);
           orderDetailsRepository.save(orderDetails);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

   /* @GetMapping("/order/confirm/{id}")
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
    }*/

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

    @PatchMapping("/admin/order/edit")
    public ResponseEntity<Object> edit( Authentication authentication,
                                             @RequestParam Long id,
                                             @RequestParam String type ) {

        User user = userServices.getByEmail(authentication.getName());
        Order order = orderService.getById(id).orElse(null);
        OrderState orderType = OrderState.valueOf(type);


        if(!user.getType().equals(UserType.ADMIN)) {
            return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
        }

        if (type.isEmpty()) {
            return new ResponseEntity<>("Order don't have any type", HttpStatus.BAD_REQUEST);
        }
        if (order == null){
            return new ResponseEntity<>("Order doesn't exist", HttpStatus.FORBIDDEN);
        }
        order.setState(orderType);
        orderService.save(order);

        return new ResponseEntity<>("Order state change",HttpStatus.OK);
    }

}
