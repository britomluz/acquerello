package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.User;

import java.io.IOException;

public interface MailService {
    public void sendMail(User user, Order order, String body);

}
