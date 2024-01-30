package com.example.shoppe.UserPages.UserFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.R;
import com.example.shoppe.UserPages.Adapters.ShopUserAdapter;
import com.example.shoppe.UserPages.UserModel.ShopUserModel;
import com.example.shoppe.databinding.FragmentShopUserBinding;


public class ShopUserFragment extends Fragment {

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


         adapter = globalFunctions.fetchDataFromFireBase(binding.CartRecyclerView ,
                AddToCart , getContext() , ShopUserModel.class , ShopUserAdapter.class);


        return  binding.getRoot();
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