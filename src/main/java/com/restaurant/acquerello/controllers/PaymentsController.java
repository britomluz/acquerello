package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.models.Card;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.services.CardService;
import com.restaurant.acquerello.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping("/api")
public class PaymentsController {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

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

        card.setBalance(card.getBalance() - amount);
        cardService.save(card);
        return new ResponseEntity<>("Payment successful", HttpStatus.CREATED);
    }

}
