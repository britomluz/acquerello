package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.ProductCategory;

//CREATED BY GABRIEL

public class ProductCategoryDTO {
    private Long id;
    private Long productId;
    private String name;
    private String description;
    private String productImage;
    private Double price;
    private Integer stock;

    public ProductCategoryDTO(ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.productId = productCategory.getProduct().getId();
        this.name = productCategory.getName();
        this.description = productCategory.getProduct().getDescription();
        this.productImage = productCategory.getProduct().getProductImage();
        this.price = productCategory.getProduct().getPrice();
        this.stock = productCategory.getProduct().getStock();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
