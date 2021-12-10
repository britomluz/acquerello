package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface BillingPDFService {
    public void exportPDF(HttpServletResponse response, User user, Order order) throws IOException;
}
