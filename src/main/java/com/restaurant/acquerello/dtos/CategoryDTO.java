package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.Category;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


//CREATED BY GABRIEL

public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String categoryImage;
    private Set<ProductCategoryDTO> products = new LinkedHashSet<>();


    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.categoryImage = category.getCategoryImage();
        this.products = category.getProductCategories().stream().map(ProductCategoryDTO::new).collect(Collectors.toSet());
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

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public Set<ProductCategoryDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductCategoryDTO> products) {
        this.products = products;
    }
}
