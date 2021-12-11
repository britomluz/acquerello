package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.models.*;
import com.restaurant.acquerello.services.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/api")
public class PaymentsController {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private MailService mailService;

    @Autowired
    private BillingPDFService billingPDFService;

    @PostMapping("/payments/{id}")
    public ResponseEntity<?> registerPayment(Authentication authentication, @PathVariable("id") Long id, @NotNull @RequestParam Double amount){
        Card card = cardService.getById(id).orElse(null);
        User user = userService.getByEmail(authentication.getName());
        if (!user.getCards().contains(card)){
            return new ResponseEntity<>("Card don't belongs you",HttpStatus.FORBIDDEN);
        }
        if (card == null){
            return new ResponseEntity<>("Card doesn't exist",HttpStatus.FORBIDDEN);
        }
        if (card.toString().isEmpty() || amount.toString().isEmpty()){
            return new ResponseEntity<>("Fields cannot be empty", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0){
            return new ResponseEntity<>("Amount cannot be less than 1", HttpStatus.FORBIDDEN);
        }
        if (card.getBalance() < amount){
            return new ResponseEntity<>("Don't have enough balance",HttpStatus.FORBIDDEN);
        }
        card.setPoints((int) (card.getPoints() + (amount/10)));
        if (card.getPoints() >= 100 && card.getPoints() < 200){
            card.setLevel(2);
        }
        if (card.getPoints() >= 200 && card.getPoints() < 300){
            card.setLevel(3);
        }
        if (card.getPoints() >= 300 && card.getPoints() < 400){
            card.setLevel(4);
        }
        if (card.getPoints() >= 400){
            card.setLevel(5);
        }
        card.setBalance(card.getBalance() - amount);
        cardService.save(card);
        return new ResponseEntity<>("Payment successful", HttpStatus.CREATED);
    }

    @PostMapping("/sendMail")
    public ResponseEntity<?> sendMail(Authentication authentication, @RequestParam Long id){
        User user = userService.getByEmail(authentication.getName());
        Order order = orderService.getById(id).orElse(null);
        List<OrderDetails> details = orderDetailsService.getAll().stream().filter(orderDetails -> orderDetails.getOrder().getId().equals(id)).collect(Collectors.toList());
        if (order == null){
            return new ResponseEntity<>("Order doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (order.getState().equals(OrderState.PENDING)){
        String body = "Hey "+user.getFirstName()+" "+user.getLastName()+"\n\nStatus: "+order.getState()+"\n\nDetail:\n";
        for (OrderDetails orderDetails : details){
           body = body.concat(Integer.toString(orderDetails.getQuantity())+"  "+orderDetails.getName()+ "  $"+orderDetails.getTotalProduct()+ "\n");

        }
            body = body.concat("\nTotal: $"+order.getTotal());
            mailService.sendMail(user,order,body);
            return new ResponseEntity<>("Send successful", HttpStatus.OK);
        }
        if (order.getState().equals(OrderState.IN_PROCESS)){
            String body = "Hey "+ order.getUser().getFirstName()+" "+order.getUser().getLastName()+" your order was accepted"+"\n\nStatus: "+order.getState()+"\n\nWe are preparing your order!";
            mailService.sendMail(order.getUser(),order,body);
            return new ResponseEntity<>("Send successful", HttpStatus.OK);
        }
        if (order.getState().equals(OrderState.DELIVERED)){
            String body = "Hey "+order.getUser().getFirstName()+" "+order.getUser().getLastName()+" your order was delivered"+"\n\nStatus: "+order.getState()+"\n\nEnjoy it and Buon apetit!";
            mailService.sendMail(order.getUser(),order,body);
            return new ResponseEntity<>("Send successful", HttpStatus.OK);
        }
        String body = "Hey "+order.getUser().getFirstName()+" "+order.getUser().getLastName()+" We are really sorry but your order was canceled"+"\n\nDon't worry in the next 24hrs we will be making the reverse of the payment";
        mailService.sendMail(order.getUser(),order,body);
        return new ResponseEntity<>("Send successful", HttpStatus.OK);
    }

    @GetMapping("/")
    public String index(){
        return "send_mail_view";
    }

    @PostMapping("/bill/pdf")
    public ResponseEntity<?> generatePDFBill(HttpServletResponse httpServletResponse, Authentication authentication, @RequestParam Long id) throws IOException {
        User user = userService.getByEmail(authentication.getName());
        Order order = orderService.getById(id).orElse(null);
        if (order == null){
            return new ResponseEntity<>("Order doesn't exist",HttpStatus.FORBIDDEN);
        }
        if (!user.getOrders().contains(order)){
            return new ResponseEntity<>("Order don't belongs you",HttpStatus.FORBIDDEN);
        }
        httpServletResponse.setContentType("application/pdf");
        DateFormat dateFormat= new SimpleDateFormat("MM-dd-yyyy_hh:mm:ss");
        String currentDatTime= dateFormat.format(new Date());

        List<OrderDetails> details = orderDetailsService.getAll().stream().filter(orderDetails -> orderDetails.getOrder().getId().equals(id)).collect(Collectors.toList());

        String headerKey = "Content-Disposition";
        String headerValue = "attacment; filename=Bill_order_No."+order.getId()+"_"+currentDatTime+".pdf";
        httpServletResponse.setHeader(headerKey,headerValue);

        billingPDFService.exportPDF(httpServletResponse, user, order, details);

        return new ResponseEntity<>("PDF created", HttpStatus.CREATED);
    }



}
