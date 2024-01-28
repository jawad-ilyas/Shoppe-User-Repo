package com.example.shoppe.Models;

public class UserData {
    String adminName , adminPhone ,  email , password , adminImage;

    public UserData() {
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminImage() {
        return adminImage;
    }

    public void setAdminImage(String adminImage) {
        this.adminImage = adminImage;
    }

    public UserData(String adminName, String adminPhone, String email, String password, String adminImage) {
        this.adminName = adminName;
        this.adminPhone = adminPhone;
        this.email = email;
        this.password = password;
        this.adminImage = adminImage;
    }
}
