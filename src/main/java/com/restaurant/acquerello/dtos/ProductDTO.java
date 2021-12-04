package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.Product;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

//CREATED BY GABRIEL

public class ProductDTO {
    private Long id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private String productImage;
    private Double price;
    private Integer stock;
    private Set<ProductCategoryDTO> categories = new LinkedHashSet<>();

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.shortDescription = product.getShortDescription();
        this.longDescription = product.getLongDescription();
        this.productImage = product.getProductImage();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.categories = product.getProductCategories().stream().map(ProductCategoryDTO::new).collect(Collectors.toSet());
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
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

    public Set<ProductCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<ProductCategoryDTO> categories) {
        this.categories = categories;
    }
}
