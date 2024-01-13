package com.example.shoppe.Adapters;

public class CategoryAdapterAdmin {

    String categoryName , categoryDescription , categoryImage;
    CategoryAdapterAdmin(){

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

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public CategoryAdapterAdmin(String categoryName, String categoryDescription, String categoryImage) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryImage = categoryImage;
    }
}
