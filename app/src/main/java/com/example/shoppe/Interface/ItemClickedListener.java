package com.example.shoppe.Interface;

import com.example.shoppe.Models.BrandModelAdmin;
import com.example.shoppe.Models.CategoryModelAdmin;
import com.example.shoppe.Models.ProductModelAdmin;
import com.example.shoppe.UserPages.UserModel.HomeUserModel;

public interface ItemClickedListener {

    default  void  productItemSelected(ProductModelAdmin data){}
    default void TotalPriceIntoCart(int price){}
    default  void  productItemSelectedUser(String nodeId , HomeUserModel data){}
    default  void categoryItemSelected(CategoryModelAdmin data){}
    default  void brandItemSelected(BrandModelAdmin data){}


}
