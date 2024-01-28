package com.example.shoppe.UserPages.UserFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppe.R;
import com.example.shoppe.databinding.FragmentHomeUserFragementBinding;


public class HomeUserFragement extends Fragment {



    public HomeUserFragement() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHomeUserFragementBinding binding;
        binding = FragmentHomeUserFragementBinding.inflate(inflater, container, false);




















        return binding.getRoot();
    }
}