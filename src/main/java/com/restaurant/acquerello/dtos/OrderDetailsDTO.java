package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.OrderDetails;
import com.restaurant.acquerello.models.OrderState;

import java.time.LocalDateTime;

public class OrderDetailsDTO {
    private Long id;
    private Long orderId;
    private String name;
    private Integer quantity;
    private Double price;
    private Double totalProduct;
    private LocalDateTime creationDate;
    private OrderState state;
    private Double totalOrder;

    public OrderDetailsDTO(OrderDetails orderDetails) {
        this.id = orderDetails.getId();
        this.orderId = orderDetails.getOrder().getId();
        this.name = orderDetails.getName();
        this.quantity = orderDetails.getQuantity();
        this.price = orderDetails.getPrice();
        this.totalProduct = orderDetails.getTotalProduct();
        this.creationDate = orderDetails.getCreationDate();
        this.state = orderDetails.getState();
        this.totalOrder = orderDetails.getTotalOrder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(Double totalProduct) {
        this.totalProduct = totalProduct;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Double getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Double totalOrder) {
        this.totalOrder = totalOrder;
    }
}
