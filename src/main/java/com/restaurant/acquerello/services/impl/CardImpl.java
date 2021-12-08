package com.restaurant.acquerello.services.impl;

import com.restaurant.acquerello.models.Card;
import com.restaurant.acquerello.repositories.CardRepository;
import com.restaurant.acquerello.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<Card> getAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Optional<Card> getById(Long id) {
        return cardRepository.findById(id);
    }
}
