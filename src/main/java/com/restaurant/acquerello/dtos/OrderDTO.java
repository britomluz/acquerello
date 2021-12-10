package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.OrderState;

import java.time.LocalDateTime;

public class OrderDTO {
    private Long id;
    private LocalDateTime creationDate;
    private LocalDateTime aceptedDate;
    private OrderState state;
    private Double total;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.creationDate = order.getCreationDate();
        this.aceptedDate = order.getAceptedDate();
        this.state = order.getState();
        this.total = order.getTotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
