package com.example.shoppe.Models;

public class CategoryModelAdmin {

    String categoryImage  , categoryName , categoryDescription;


     public CategoryModelAdmin(){};
    public CategoryModelAdmin( String categoryName, String categoryDescription ,String categoryImage) {
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }


    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
