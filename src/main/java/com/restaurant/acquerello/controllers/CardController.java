package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.CardDTO;
import com.restaurant.acquerello.models.Card;
import com.restaurant.acquerello.models.CardType;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.models.UserType;
import com.restaurant.acquerello.services.CardService;
import com.restaurant.acquerello.services.UserService;
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
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @GetMapping("/cards")
    public ResponseEntity<?> getAllCards(){
        try{
            return new ResponseEntity<>(Util.makeDTO("cards", cardService.getAll().stream().map(CardDTO::new).collect(Collectors.toList())), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error in request",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cards/create")
    public ResponseEntity<?> createCard(Authentication authentication){
        User user = userService.getByEmail(authentication.getName());
        if (user == null){
            return new ResponseEntity<>("Don't have authority",HttpStatus.FORBIDDEN);
        }

        if (user.getCards().stream().anyMatch(card -> card.getType().equals(CardType.ACTIVE))){
            return new ResponseEntity<>("Only 1 card allowed",HttpStatus.FORBIDDEN);
        }
        List<String> getAllCardNumber = cardService.getAll().stream().map(Card::getNumber).collect(Collectors.toList());
        String cardNumber = Util.cardNumber();
        while (getAllCardNumber.contains(cardNumber)){
            cardNumber = Util.cardNumber();
        }

        Card newCard = new Card(cardNumber, Util.cvvCard(), LocalDateTime.now(), 0D, 25, 1, CardType.ACTIVE, user);
        cardService.save(newCard);
        return new ResponseEntity<>("Card created",HttpStatus.CREATED);
    }

    @PatchMapping("/cards/delete/{id}")
    public ResponseEntity<?> deleteCard(Authentication authentication, @PathVariable("id") Long id){
        Card card = cardService.getById(id).orElse(null);
        User user = userService.getByEmail(authentication.getName());

        if (!user.getType().equals(UserType.USER)){
            return new ResponseEntity<>("Don't have authority",HttpStatus.FORBIDDEN);
        }
        if (!user.getCards().contains(card)){
            return new ResponseEntity<>("Card don't belongs you",HttpStatus.FORBIDDEN);
        }
        assert card != null;
        if (card.getType() == null){
            return new ResponseEntity<>("Card doesn't exist",HttpStatus.FORBIDDEN);
        }
        if (card.getBalance()  > 0){
            return new ResponseEntity<>("Balance need be 0 to delete",HttpStatus.FORBIDDEN);
        }
        card.setType(CardType.INACTIVE);
        cardService.save(card);
        return new ResponseEntity<>("Card deleted",HttpStatus.OK);
    }


    @PatchMapping("/cards/add-balance/{id}")
    public ResponseEntity<?> addBalance(Authentication authentication, @PathVariable("id") Long id, @RequestParam Double amount){
        Card card = cardService.getById(id).orElse(null);
        User user = userService.getByEmail(authentication.getName());

        if (user == null){
            return new ResponseEntity<>("Don't have authority",HttpStatus.UNAUTHORIZED);
        }
        if (!user.getCards().contains(card)){
            return new ResponseEntity<>("Card don't belongs you",HttpStatus.FORBIDDEN);
        }
        assert card != null;
        if (card.getType() == null){
            return new ResponseEntity<>("Card doesn't exist",HttpStatus.FORBIDDEN);
        }
        if (!card.getType().equals(CardType.ACTIVE)){
            return new ResponseEntity<>("Inactive card",HttpStatus.FORBIDDEN);
        }
        if(card.getBalance() == 0 && amount >= 100){
            card.setPoints(card.getPoints() + 10);
        }

        card.setBalance(card.getBalance() + amount);
        cardService.save(card);
        return new ResponseEntity<>("Add balance OK",HttpStatus.OK);
    }

}
