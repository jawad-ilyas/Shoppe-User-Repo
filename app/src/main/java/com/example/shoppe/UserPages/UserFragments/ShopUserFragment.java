package com.example.shoppe.UserPages.UserFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.Interface.ItemClickedListener;
import com.example.shoppe.R;
import com.example.shoppe.UserPages.Adapters.ShopUserAdapter;
import com.example.shoppe.UserPages.UserModel.ShopUserModel;
import com.example.shoppe.databinding.FragmentShopUserBinding;


public class ShopUserFragment extends Fragment implements ItemClickedListener {

    ShopUserAdapter adapter;
    String AddToCart = "AddToCart/";
    FragmentShopUserBinding binding;

    public ShopUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentShopUserBinding.inflate(inflater , container , false);

        GlobalFunctions globalFunctions = new GlobalFunctions();

        SharedPreferences preferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String email = preferences.getString("email", "no email id");
        String userId = preferences.getString("userId", "no userIid");
         adapter = globalFunctions.fetchDataFromFireBase(binding.CartRecyclerView ,
                AddToCart+userId , getContext() , ShopUserModel.class , ShopUserAdapter.class ,false);

        adapter.clickedListener = this; // Make sure this line is present



        String totalPrice = binding.setTotalPrice.getText().toString();




        if(totalPrice.equals("Total"))
        {
//            Toast.makeText(getContext(), "if " + totalPrice, Toast.LENGTH_SHORT).show();
            binding.checkoutPage.setEnabled(false);
            binding.CheckoutTitle.setText("No Item Add To Cart");
        }
        else{
            binding.checkoutPage.setEnabled(true);
            binding.CheckoutTitle.setText("Items into cart");
        }



        binding.checkoutPage.setOnClickListener(v -> {

            startActivity(new Intent(getContext(), CheckoutActivity.class));


        });







        return  binding.getRoot();
    }
    public void TotalPriceIntoCart(int price) {
//        Toast.makeText(getContext(), "i am total Price" + price, Toast.LENGTH_SHORT).show();
        binding.setTotalPrice.setText(String.valueOf(price));

        if(price !=0)
        {
            binding.checkoutPage.setEnabled(true);
            binding.CheckoutTitle.setText("Items into cart");
        }

    }
    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }



}