package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.OrderDetails;

public class OrderDetailsDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private Double total;

    public OrderDetailsDTO(OrderDetails orderDetails) {
        this.id = orderDetails.getId();
        this.name = orderDetails.getName();
        this.quantity = orderDetails.getQuantity();
        this.price = orderDetails.getPrice();
        this.total = orderDetails.getTotal();
    }

    public Long getId() {
        return id;
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
}
