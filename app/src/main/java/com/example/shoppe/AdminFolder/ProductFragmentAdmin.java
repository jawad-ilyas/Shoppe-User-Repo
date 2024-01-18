package com.example.shoppe.AdminFolder;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppe.Adapters.ProductModelAdapterAdmin;
import com.example.shoppe.Adapters.ProductModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.databinding.FragmentProductAdminBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class ProductFragmentAdmin extends Fragment {


    FragmentProductAdminBinding binding;
    ProductModelAdapterAdmin adapter;

    public ProductFragmentAdmin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentProductAdminBinding.inflate(inflater,container, false);


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

        binding.productRecyclerView.setLayoutManager( new GridLayoutManager(getContext() , 2));

//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("productsDetail");
//
//
//        FirebaseRecyclerOptions<ProductModelAdmin> options =
//                new FirebaseRecyclerOptions.Builder<ProductModelAdmin>()
//                        .setQuery(query, ProductModelAdmin.class)
//                        .build();
//
//
//        adapter = new ProductModelAdapterAdmin(options);
//        binding.productRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter !=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter !=null)
            adapter.stopListening();
    }

}
