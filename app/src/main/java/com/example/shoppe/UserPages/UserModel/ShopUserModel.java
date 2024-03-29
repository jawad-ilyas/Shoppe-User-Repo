package com.example.shoppe.UserPages.UserModel;

public class ShopUserModel {

    String productName , productDescription  , productPrice , productImage;
    int productQuantity  = 0 ;
    public ShopUserModel() {

    }

    public ShopUserModel(String productName, String productDescription, String productPrice, String productImage) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }
    public ShopUserModel(String productName, String productDescription, String productPrice,
                         String productImage ,int productQuantity) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }
    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
