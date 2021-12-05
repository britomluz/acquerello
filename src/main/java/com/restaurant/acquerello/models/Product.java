package com.restaurant.acquerello.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

//CREATED BY GABRIEL

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private String name;
    private String description;
    private String productImage;
    private Double price;
    private Integer stock;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ProductCategory> productCategories = new LinkedHashSet<>();

    public Product() {
    }

    public Product(String name, String description, String productImage, Double price, Integer stock) {
        this.name = name;
        this.description = description;
        this.productImage = productImage;
        this.price = price;
        this.stock = stock;
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

    public Set<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(Set<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }
}
