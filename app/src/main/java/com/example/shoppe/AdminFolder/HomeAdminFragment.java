package com.example.shoppe.AdminFolder;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppe.R;
import com.example.shoppe.databinding.FragmentHomeAdminBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeAdminFragment extends Fragment {


    public HomeAdminFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHomeAdminBinding binding;
        binding = FragmentHomeAdminBinding.inflate(inflater , container , false);


        binding.moveToBrandPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BrandPageActivityAdmin.class));

            }
        });


        binding.moveToCategoryPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , CategoryPageActivityAdmin.class));

            }
        });



        binding.moveToProductPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , ProductPageActivityAdmin.class));

            }
        });


        return binding.getRoot();
    }
}