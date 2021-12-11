package com.restaurant.acquerello.services;

import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.OrderDetails;
import com.restaurant.acquerello.models.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface BillingPDFService {
    public void exportPDF(HttpServletResponse response, User user,Order order, List<OrderDetails> orderDetails) throws IOException;
}
