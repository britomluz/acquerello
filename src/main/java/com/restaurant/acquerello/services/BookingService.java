package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Booking;

import java.util.List;

public interface BookingService {
    public List<Booking> getAll();
    public Booking getBookingById(Long id);
    public void save(Booking booking);
}
