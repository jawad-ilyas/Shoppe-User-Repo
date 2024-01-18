package com.example.shoppe.Models;

public class BrandModelAdmin {

    String brandImage , brandDescription, brandName ;


    public BrandModelAdmin() {

    }


    public String getBrandImage() {
        return brandImage;
    }

    public void setBrandImage(String brandImage) {
        this.brandImage = brandImage;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public BrandModelAdmin(String brandName, String brandDescription, String brandImage) {
        this.brandImage = brandImage;
        this.brandDescription = brandDescription;
        this.brandName = brandName;
    }
}
