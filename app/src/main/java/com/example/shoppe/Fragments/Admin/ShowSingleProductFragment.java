package com.example.shoppe.Fragments.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppe.Models.ProductModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.databinding.FragmentShowSingleProductBinding;

public class ShowSingleProductFragment extends Fragment {

    ProductModelAdmin data;
    public ShowSingleProductFragment() {
        // Required empty public constructor
    }
    public ShowSingleProductFragment(ProductModelAdmin data) {
      this.data = data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentShowSingleProductBinding binding;
        binding = FragmentShowSingleProductBinding.inflate(inflater, container , false);

        Glide.with(getContext()).load(data.getProductImage()).into(binding.productImage);
        binding.productName.setText(data.getProductName());
        binding.productDescription.setText(data.getProductDescription());
        binding.productPrice.setText(data.getProductPrice());








        return binding.getRoot();
    }
}