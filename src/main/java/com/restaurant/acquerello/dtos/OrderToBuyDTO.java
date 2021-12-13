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

    public OrderToBuyDTO(List<Product> products, Double total, OrderType type, Long id) {
        this.products = products;
        this.total = total;
        this.type = type;
        this.id = id;
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
}
