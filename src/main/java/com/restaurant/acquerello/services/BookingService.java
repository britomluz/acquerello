package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Booking;
import com.restaurant.acquerello.models.OrderDetails;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    public List<Booking> getAll();
    public void save(Booking booking);
    public Optional<Booking> getById(Long id);
}
