package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.OrderDetails;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsService {
    public List<OrderDetails> getAll();
    public Optional<OrderDetails> getById(Long id);
    public OrderDetails save(OrderDetails orderDetails);
    public List<OrderDetails> listAll();
    public void deleteOrderDetails(OrderDetails orderDetails);
}
