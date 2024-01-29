package com.example.shoppe.UserPages.UserFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.UserPages.Adapters.HomeUserAdapter;
import com.example.shoppe.UserPages.UserModel.HomeUserModel;
import com.example.shoppe.databinding.FragmentHomeUserFragementBinding;


public class HomeUserFragement extends Fragment {


    String productsDetail = "productsDetail/";
    String productName = "productName";
    HomeUserAdapter adapter;
    FragmentHomeUserFragementBinding binding;
    public HomeUserFragement() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHomeUserFragementBinding.inflate(inflater, container, false);

        GlobalFunctions globalFunctions = new GlobalFunctions();

      adapter = globalFunctions.fetchDataFromFireBase(binding.homeProductUserRecyclerView, productsDetail,
              getContext() , HomeUserModel.class , HomeUserAdapter.class);


        return binding.getRoot();
    }
    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.startListening();
        else
            Toast.makeText(getContext(), "Adapter is null", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter !=null)
            adapter.stopListening();
        else
            Toast.makeText(getContext(), "Adapter is null", Toast.LENGTH_SHORT).show();
    }
}
