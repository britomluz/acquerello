package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    public List<Card> getAll();
    public Card save(Card card);
    public Optional<Card> getById(Long id);
    Card findById(Long id);
}
