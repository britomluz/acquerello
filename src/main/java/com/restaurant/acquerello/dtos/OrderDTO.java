package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.OrderDetails;

import java.util.List;

public class OrderDTO {
    private String name;
    private Integer quantity;
    private Double price;
    private Double total;
    private List<OrderDetails> orders;

    public OrderDTO(String name, Integer quantity, Double price, Double total, List<OrderDetails> orders) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }
}
