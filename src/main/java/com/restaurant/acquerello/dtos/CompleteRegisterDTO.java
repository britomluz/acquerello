package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.OrderType;
import com.restaurant.acquerello.models.Product;

import java.time.LocalDateTime;
import java.util.List;

public class CompleteRegisterDTO {
    // get users info
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long number;

    // get address info
    private String street;
    private Integer numberStreet;
    private String zip;
    private String state;
    private String reference;

    // get products and total for new order
    private List<Product> products;
    private Double total;
    private OrderType type;

    public CompleteRegisterDTO(String firstName, String lastName, String email, String password, Long number, String street, Integer numberStreet, String zip, String state, String reference, List<Product> products, Double total, OrderType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.number = number;
        this.street = street;
        this.numberStreet = numberStreet;
        this.zip = zip;
        this.state = state;
        this.reference = reference;
        this.products = products;
        this.total = total;
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumberStreet() {
        return numberStreet;
    }

    public void setNumberStreet(Integer numberStreet) {
        this.numberStreet = numberStreet;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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
}
