package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.ProductCategory;

//CREATED BY GABRIEL

public class ProductCategoryDTO {
    private Long id;
    private Long categoryId;
    private String name;

    public ProductCategoryDTO(ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.categoryId = productCategory.getCategory().getId();
        this.name = productCategory.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
