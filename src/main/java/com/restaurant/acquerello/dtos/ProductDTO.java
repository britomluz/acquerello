package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.Product;
import com.restaurant.acquerello.models.ProductCategory;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

//CREATED BY GABRIEL

public class ProductDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private String description;
    private String productImage;
    private Double price;
    private Integer stock;
    private Set<ProductCategoryDTO> productCategories = new LinkedHashSet<>();


    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.productImage = product.getProductImage();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.productCategories = product.getProductCategories().stream().map(ProductCategoryDTO::new).collect(Collectors.toSet());
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Set<ProductCategoryDTO> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(Set<ProductCategoryDTO> productCategories) {
        this.productCategories = productCategories;
    }
}
