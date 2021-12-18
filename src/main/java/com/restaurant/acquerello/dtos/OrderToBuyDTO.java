package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.OrderType;
import com.restaurant.acquerello.models.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderToBuyDTO {
    private List<Product> products = new ArrayList<>();
    private Double total;
    private OrderType type;
    private Long id;
    private Integer tableNumber;

    // field if the user create address
    private String street;
    private Integer numberStreet;
    private String zip;
    private String state;
    private String reference;

    public OrderToBuyDTO(List<Product> products, Double total, OrderType type, Long id, String street, Integer numberStreet, String zip, String state, String reference, Integer tableNumber) {
        this.products = products;
        this.total = total;
        this.type = type;
        this.id = id;
        this.street = street;
        this.numberStreet = numberStreet;
        this.zip = zip;
        this.state = state;
        this.reference = reference;
        this.tableNumber = tableNumber;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }
}
