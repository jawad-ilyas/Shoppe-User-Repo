package com.example.shoppe.UserPages.UserModel;

public class ProductDetails {
    private String productName;
    private String productDescription;
    private String productPrice;
    private String productImage;

    public ProductDetails(String productName, String productDescription, String productPrice, String productImage) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}
