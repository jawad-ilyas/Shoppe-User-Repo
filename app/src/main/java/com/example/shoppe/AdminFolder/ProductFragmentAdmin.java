package com.example.shoppe.AdminFolder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoppe.Adapters.ProductModelAdapterAdmin;
import com.example.shoppe.Models.ProductModelAdmin;
import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.databinding.FragmentProductAdminBinding;


public class ProductFragmentAdmin extends Fragment {


    String productsDetail = "productsDetail/";
    String productName = "productName";
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


        GlobalFunctions globalFunctions = new GlobalFunctions();

        binding.searchProduct.setOnClickListener(v -> {
            String query = globalFunctions.FieldText(binding.etSearchProduct);
            Toast.makeText(getContext(), "" + query, Toast.LENGTH_SHORT).show();
            adapter = globalFunctions.searchDataFromFireBase(binding.productRecyclerView,productsDetail,getContext(),
                    ProductModelAdmin.class, ProductModelAdapterAdmin.class, productName ,query );
            adapter.startListening();
        });

        adapter =  globalFunctions.fetchDataFromFireBase(binding.productRecyclerView,
                productsDetail , getContext(), ProductModelAdmin.class , ProductModelAdapterAdmin.class);










//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("productsDetail");
//        FirebaseRecyclerOptions<ProductModelAdmin> options =
//                new FirebaseRecyclerOptions.Builder<ProductModelAdmin>()
//                        .setQuery(query, ProductModelAdmin.class)
//                        .build();
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
