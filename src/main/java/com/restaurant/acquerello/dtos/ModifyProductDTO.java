package com.restaurant.acquerello.dtos;

public class ModifyProductDTO {
    private String name;
    private String description;
    private String productImage;
    private Double price;
    private Integer stock;

    public ModifyProductDTO(String name, String description, String productImage, Double price, Integer stock) {
        this.name = name;
        this.description = description;
        this.productImage = productImage;
        this.price = price;
        this.stock = stock;
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
