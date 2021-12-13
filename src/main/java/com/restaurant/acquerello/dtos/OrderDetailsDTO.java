package com.restaurant.acquerello.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurant.acquerello.models.Address;
import com.restaurant.acquerello.models.OrderDetails;
import com.restaurant.acquerello.models.OrderState;

import java.time.LocalDateTime;
import java.util.List;

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
    private String imageProduct;
    private Address address;

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
        this.imageProduct = orderDetails.getProduct().getProductImage();
        this.address = orderDetails.getOrder().getAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
