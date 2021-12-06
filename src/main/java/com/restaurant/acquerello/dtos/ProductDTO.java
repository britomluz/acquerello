package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.Product;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

//CREATED BY GABRIEL

public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String productImage;
    private Double price;
    private Integer stock;


    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.productImage = product.getProductImage();
        this.price = product.getPrice();
        this.stock = product.getStock();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
