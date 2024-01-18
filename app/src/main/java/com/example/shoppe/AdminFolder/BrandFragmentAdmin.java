package com.example.shoppe.AdminFolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoppe.Adapters.BrandAdapterAdmin;
import com.example.shoppe.Adapters.CategoryAdapterAdmin;
import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.Models.BrandModelAdmin;
import com.example.shoppe.Models.CategoryModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.databinding.FragmentBrandAdminBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class BrandFragmentAdmin extends Fragment {

    final String brandDetail = "brandDetail";
    final Context context = getContext();
    BrandAdapterAdmin adapter;
    FragmentBrandAdminBinding binding;
    GlobalFunctions globalFunctions;
    public BrandFragmentAdmin() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        binding = FragmentBrandAdminBinding.inflate(inflater, container, false);
        globalFunctions = new GlobalFunctions();  // Initialize globalFunctions

        // searching Login For Brand
        binding.searchBrand.setOnClickListener( v -> {

            String query = binding.etSearchBrand.getText().toString().trim();
          adapter =   globalFunctions.searchDataFromFireBase(binding.brandFragmentRecyclerView ,brandDetail ,
                    context ,BrandModelAdmin.class ,  BrandAdapterAdmin.class ,
                    "brandName" ,  query);
//            searchFromFireBase(query);

            adapter.startListening();
            Toast.makeText(getContext(), "Click on Searching" + query, Toast.LENGTH_SHORT).show();

        });








        binding.moveToBrandPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BrandPageActivityAdmin.class));
            }
        });



//        fetchDataFromFireBase();
       adapter =  globalFunctions.fetchDataFromFireBase(binding.brandFragmentRecyclerView ,brandDetail ,
                context ,BrandModelAdmin.class ,  BrandAdapterAdmin.class );
        adapter.startListening();
        return binding.getRoot();
    }
    void searchFromFireBase(String search){


        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("brandDetail").orderByChild("brandName").startAt(search).endAt(search + "\uf8ff");

        FirebaseRecyclerOptions<BrandModelAdmin> options =
                new FirebaseRecyclerOptions.Builder<BrandModelAdmin>()
                        .setQuery(query, BrandModelAdmin.class)
                        .build();


        adapter = new BrandAdapterAdmin(options);

        adapter.startListening();
        binding.brandFragmentRecyclerView.setAdapter(adapter);

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