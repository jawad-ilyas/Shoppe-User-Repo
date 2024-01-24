package com.example.shoppe.Fragments.Admin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.shoppe.Models.BrandModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.databinding.FragmentShowSingleBrandBinding;

public class ShowSingleBrandFragment extends Fragment {
    BrandModelAdmin data;
    private FragmentShowSingleBrandBinding binding;

    public ShowSingleBrandFragment() {
        // Required empty public constructor
    }

    public ShowSingleBrandFragment(BrandModelAdmin data) {
        this.data = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShowSingleBrandBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.brandDescription.setText(data.getBrandDescription());
        binding.brandName.setText(data.getBrandName());
        Glide.with(getContext()).load(data.getBrandImage()).into(binding.brandImage);
        // Now you can access views using the binding object, e.g., binding.textView.setText("Hello");

        return view;
    }
}
