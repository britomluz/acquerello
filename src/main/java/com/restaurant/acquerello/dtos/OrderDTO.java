package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.OrderState;
import com.restaurant.acquerello.models.OrderType;

import java.time.LocalDateTime;

public class OrderDTO {
    private Long id;
    private String email;
    private LocalDateTime creationDate;
    private LocalDateTime aceptedDate;
    private OrderState state;
    private Double total;
    private OrderType type;
    private Integer tableNumber;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.email = order.getUser().getEmail();
        this.creationDate = order.getCreationDate();
        this.aceptedDate = order.getAceptedDate();
        this.state = order.getState();
        this.total = order.getTotal();
        this.type = order.getType();
        this.tableNumber = order.getTableNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getAceptedDate() {
        return aceptedDate;
    }

    public void setAceptedDate(LocalDateTime aceptedDate) {
        this.aceptedDate = aceptedDate;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }
}
