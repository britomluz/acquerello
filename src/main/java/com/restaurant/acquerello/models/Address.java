package com.restaurant.acquerello.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

// Created by Brian

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String street;
    private Integer number;
    private String zip;
    private String state;
    private String reference;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userAdd;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    public Address() {}

    public Address(String street, Integer number, String zip, String state, String reference) {
        this.street = street;
        this.number = number;
        this.zip = zip;
        this.state = state;
        this.reference = reference;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
    @JsonIgnore
    public User getUser() {
        return userAdd;
    }
    @JsonIgnore
    public void setUser(User user) {
        this.userAdd = user;
    }
    @JsonIgnore
    public User getUserAdd() {
        return userAdd;
    }
    @JsonIgnore
    public void setUserAdd(User userAdd) {
        this.userAdd = userAdd;
    }
    @JsonIgnore
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    @JsonIgnore
    public void addOrder(Order order) {
        order.setAddress(this);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Address{");
        sb.append("id=").append(id);
        sb.append(", street='").append(street).append('\'');
        sb.append(", number=").append(number);
        sb.append(", zip='").append(zip).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", reference='").append(reference).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
