package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.OrderDTO;
import com.restaurant.acquerello.dtos.OrderToBuyDTO;
import com.restaurant.acquerello.dtos.OrderDetailsDTO;
import com.restaurant.acquerello.models.*;
import com.restaurant.acquerello.repositories.OrderDetailsRepository;
import com.restaurant.acquerello.repositories.OrderRepository;
import com.restaurant.acquerello.services.AddressService;
import com.restaurant.acquerello.services.ProductService;
import com.restaurant.acquerello.services.UserService;
import com.restaurant.acquerello.services.impl.OrderDetailsImpl;
import com.restaurant.acquerello.services.impl.OrderServiceImpl;
import com.restaurant.acquerello.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
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
    private AddressService addressService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderDetailsImpl orderDetailsService;

    @Autowired
    private ProductService productService;


    @GetMapping("/order")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAll().stream().map(OrderDTO::new).collect(Collectors.toList());
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<Object> getOrders(Authentication authentication, @PathVariable Long id){

        User user = userServices.getByEmail(authentication.getName());
        Order order = orderService.getById(id).orElse(null);

        if(user.getType().equals(UserType.ADMIN)){
            return new ResponseEntity<>(orderService.getById(id).map(OrderDTO::new).orElse(null), HttpStatus.CREATED);
        }

        if(!user.getOrders().contains(order)){
            return new ResponseEntity<>("Order incorrect",HttpStatus.CONFLICT);
        }
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
    public ResponseEntity<?> getAllOrderDetails(Authentication authentication) {
        User user = userServices.getByEmail(authentication.getName());

        return new ResponseEntity<>(orderDetailsService.getAll().stream().map(OrderDetailsDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }


    @GetMapping("/order/details/{id}")
    public ResponseEntity<?> getAllOrderDetailsById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(orderDetailsService.getAll().stream().filter(orderDetails -> orderDetails.getOrder().getId().equals(id)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/order/buy")
    public ResponseEntity<?> createOrder(Authentication authentication, @RequestBody OrderToBuyDTO orderToBuyDTO) {

        User user = userServices.getByEmail(authentication.getName());


        if(orderToBuyDTO.getProducts().size() < 1) {
            return new ResponseEntity<>("Dont have products selected", HttpStatus.FORBIDDEN);
        }

        if (user == null){
            return new ResponseEntity<>("User doesn't exist", HttpStatus.FORBIDDEN);
        }

        if(orderToBuyDTO.getTotal() == null || orderToBuyDTO.getTotal() < 1) {
            return new ResponseEntity<>("Fields cannot are empty", HttpStatus.FORBIDDEN);
        }

        if(orderToBuyDTO.getType().equals(OrderType.LOCAL)){
            if(orderToBuyDTO.getTableNumber() < 1 || orderToBuyDTO.getTableNumber() == null){
                return new ResponseEntity<>("Please, write the table number", HttpStatus.FORBIDDEN);

            }
        }

        Order order = new Order(LocalDateTime.now(), LocalDateTime.now(), OrderState.PENDING, orderToBuyDTO.getTotal(), orderToBuyDTO.getType(), orderToBuyDTO.getTableNumber());
        user.addOrder(order);
        orderService.save(order);



        if(orderToBuyDTO.getType().equals(OrderType.DELIVERY)) {
            if(orderToBuyDTO.getId() < 1) {
                Address address = new Address(orderToBuyDTO.getStreet(), orderToBuyDTO.getNumberStreet(), orderToBuyDTO.getZip(), orderToBuyDTO.getState(), orderToBuyDTO.getReference());
                user.addAddress(address);
                order.addAddress(address);
                addressService.save(address);
            }else {
                Address address = addressService.getAddressBydId(orderToBuyDTO.getId());
                order.addAddress(address);
            }
        }

        for (Product product : orderToBuyDTO.getProducts()){
            Integer quantity = product.getQuantity();
            Long idProduct = product.getId();
            Product product1 = productService.getById(idProduct).orElse(null);
            if (product1 == null){
                return new ResponseEntity<>("Product doesn't exist", HttpStatus.FORBIDDEN);
            }
            if (product1.getStock() < quantity){
                return new ResponseEntity<>("Sorry we don't have enough stock", HttpStatus.FORBIDDEN);
            }
            OrderDetails orderDetails = new OrderDetails(quantity,product1,order);

            orderDetailsService.save(orderDetails);
        }

        return new ResponseEntity<>("Order No."+order.getId(),HttpStatus.ACCEPTED);
    }
//consultar y de no servir borrar
   @GetMapping("/order/confirm/{id}")
    public ResponseEntity<?> confirmOrder(Authentication authentication, @PathVariable Long id) {

        User user = userServices.getByEmail(authentication.getName());

        if(user.getType().equals(UserType.USER)) {
            return new ResponseEntity<>("Access denied",HttpStatus.FORBIDDEN);
        }

        // search the order by id

        Order order = orderService.getById(id).orElse(null);
        if (order == null){
            return new ResponseEntity<>("Order doesn't exist", HttpStatus.FORBIDDEN);
        }
        order.setAceptedDate(LocalDateTime.now());

        orderService.save(order);

        return new ResponseEntity<>("Order created No."+order.getId(),HttpStatus.ACCEPTED);
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
        if (order.getState().equals(OrderState.CANCELED)){
            return new ResponseEntity<>("Order was canceled", HttpStatus.FORBIDDEN);
        }
        if (order.getState().equals(OrderState.DELIVERED)){
            return new ResponseEntity<>("Order was delivered", HttpStatus.FORBIDDEN);
        }
        order.setState(orderType);
        order.setAceptedDate(LocalDateTime.now());
        orderService.save(order);

        return new ResponseEntity<>("Order state change",HttpStatus.OK);
    }
//
    @RequestMapping("/order/cancel")
    public ResponseEntity<?> cancelOrder(Authentication authentication, @RequestParam Long id) {
        User user = userServices.getByEmail(authentication.getName());
        Order order = orderService.getById(id).orElse(null);

        // if the user dont have the order
        if (user == null){
            return new ResponseEntity<>("Don't have authority", HttpStatus.FORBIDDEN);
        }
        if(!user.getOrders().contains(order)) {
            return new ResponseEntity<>("Cannot found the order", HttpStatus.FORBIDDEN);
        }
        if (order == null){
            return new ResponseEntity<>("Order doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (order.getState().equals(OrderState.CANCELED)){
            return new ResponseEntity<>("Order already canceled", HttpStatus.FORBIDDEN);
        }

        order.setState(OrderState.CANCELED);

        orderService.save(order);

        return  new ResponseEntity<>("Order cancelled", HttpStatus.ACCEPTED);
    }




}
