package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderToBuyDTO {
    private List<Product> products = new ArrayList<>();
    private Double total;
    private String type;

    public OrderToBuyDTO(List<Product> products, Double total, String type) {
        this.products = products;
        this.total = total;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
