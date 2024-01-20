package com.example.shoppe.AdminFolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppe.Adapters.BrandAdapterAdmin;
import com.example.shoppe.Adapters.CategoryAdapterAdmin;
import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.Models.BrandModelAdmin;
import com.example.shoppe.Models.CategoryModelAdmin;
import com.example.shoppe.databinding.FragmentCategoryAdminBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class CategoryFragmentAdmin extends Fragment {

    GlobalFunctions globalFunctions;
    CategoryAdapterAdmin adapter;
    Context context  = getContext();

    String categoryDetail= "categoryDetail";
    FragmentCategoryAdminBinding binding;
    public CategoryFragmentAdmin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        binding = FragmentCategoryAdminBinding.inflate(inflater,container, false);
        globalFunctions = new GlobalFunctions();  // Initialize globalFunctions
        binding.searchCategory.setOnClickListener(v -> {

            String query = binding.etSearchCategory.getText().toString().trim();
            adapter =   globalFunctions.searchDataFromFireBase(binding.CategoryFragmentRecyclerView ,categoryDetail ,
                    context ,CategoryModelAdmin.class ,  CategoryAdapterAdmin.class ,
                    "categoryName" ,  query);

            adapter.startListening();

        });

//        binding.moveToCategoryPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext() , CategoryPageActivityAdmin.class));
//
//
//
//            }
//        });


        adapter =  globalFunctions.fetchDataFromFireBase(binding.CategoryFragmentRecyclerView ,categoryDetail ,
                context , CategoryModelAdmin.class ,  CategoryAdapterAdmin.class );
        return binding.getRoot();
    }
    void fetchDataFromFireBase(){

        binding.CategoryFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("categoryDetail");

        FirebaseRecyclerOptions<CategoryModelAdmin> options =
                new FirebaseRecyclerOptions.Builder<CategoryModelAdmin>()
                        .setQuery(query, CategoryModelAdmin.class)
                        .build();


        adapter = new CategoryAdapterAdmin(options);

        binding.CategoryFragmentRecyclerView.setAdapter(adapter);

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