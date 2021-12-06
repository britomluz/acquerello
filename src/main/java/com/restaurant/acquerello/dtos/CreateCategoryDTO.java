package com.restaurant.acquerello.dtos;

public class CreateCategoryDTO {
    private String name;
    private String description;
    private String categoryImage;

    public CreateCategoryDTO(String name, String description, String categoryImage) {
        this.name = name;
        this.description = description;
        this.categoryImage = categoryImage;
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
}
