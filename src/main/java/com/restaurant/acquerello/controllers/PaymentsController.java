package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.models.Card;
import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.services.CardService;
import com.restaurant.acquerello.services.MailService;
import com.restaurant.acquerello.services.OrderService;
import com.restaurant.acquerello.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    private MailService mailService;

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
        assert order != null;
        String body = "Hey "+user.getFirstName()+" "+"\n\n Status: "+order.getState()+"\n\n Total: "+order.getTotal();
        mailService.sendMail(user,order,body);
        return new ResponseEntity<>("Send", HttpStatus.OK);
    }

    @GetMapping("/")
    public String index(){
        return "send_mail_view";
    }

   /* @PostMapping("/sendMailTo")
    public String sendMail(@RequestParam("name") String name, @RequestParam("mail") String mail, @RequestParam("subject") String subject, @RequestParam("body") String body){

        String message = body +"\n\n\nDatos de contacto: " + "\nNombre: " + name + "\nE-mail: " + mail;
        mailService.sendMailTo(mail, subject, message);

        return "send_mail_view";
    }*/

}
