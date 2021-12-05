package com.restaurant.acquerello.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders_list")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(strategy =  "native", name = "native")
    private Long id;
    private LocalDateTime creationDate;
    private LocalDateTime aceptedDate;
    private OrderState state;
    private Double total;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_oder")
    private User user;

    public Order() {}

    public Order(LocalDateTime creationDate, LocalDateTime aceptedDate, OrderState state, Double total) {
        this.creationDate = creationDate;
        this.aceptedDate = aceptedDate;
        this.state = state;
        this.total = total;
    }

    public Long getId() {
        return id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Order{");
        sb.append("id=").append(id);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", aceptedDate=").append(aceptedDate);
        sb.append(", state=").append(state);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
