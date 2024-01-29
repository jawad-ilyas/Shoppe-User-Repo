package com.example.shoppe.UserPages.SingleScreen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.shoppe.R;
import com.example.shoppe.UserPages.UserModel.HomeUserModel;
import com.example.shoppe.databinding.FragmentSingleProductBinding;

public class SingleProductFragment extends Fragment {

    private FragmentSingleProductBinding binding;
    private HomeUserModel data;

    public SingleProductFragment() {
        // Required empty public constructor
    }

    public SingleProductFragment(HomeUserModel data) {
        this.data = data;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSingleProductBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        binding.productImage.setImageResource(data.getProductImage());
        Glide.with(getContext()).load(data.getProductImage()).into(binding.productImage);
        binding.productDescription.setText(data.getProductDescription());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release the binding when the view is destroyed
    }
}
