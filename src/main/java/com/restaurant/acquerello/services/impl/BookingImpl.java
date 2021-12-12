package com.restaurant.acquerello.services.impl;

import com.restaurant.acquerello.models.Booking;
import com.restaurant.acquerello.repositories.BookingRepository;
import com.restaurant.acquerello.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingImpl implements BookingService {

    @Autowired
    public BookingRepository bookingRepository;

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public Optional<Booking> getById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking getTable(Integer table) {
        return bookingRepository.getByTableNumber(table);
    }
}
