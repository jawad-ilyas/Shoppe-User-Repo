package com.example.shoppe.Fragments.Admin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppe.Models.CategoryModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.databinding.FragmentShowSingleCategoryBinding;

public class ShowSingleCategoryFragment extends Fragment {
    CategoryModelAdmin data;
    private FragmentShowSingleCategoryBinding binding;

    public ShowSingleCategoryFragment() {
        // Required empty public constructor
    }

    public ShowSingleCategoryFragment(CategoryModelAdmin data) {

        this.data = data;
//
//        if(data.getCategoryDescription() !=null)
//        {
//            Toast.makeText(getContext(), "" +data.getCategoryDescription(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(), "" +data.getCategoryName(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(), "" +data.getCategoryImage(), Toast.LENGTH_SHORT).show();
//
//        }
//        else {
//
//            Toast.makeText(getContext(), "" +data.getCategoryName(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(), "" +data.getCategoryImage(), Toast.LENGTH_SHORT).show();
//        }

//        binding.categoryDescription.setText(data.getCategoryDescription());
//        binding.categoryName.setText(data.getCategoryName());
//        Glide.with(getContext()).load(data.getCategoryImage()).into(binding.categoryImage);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowSingleCategoryBinding.inflate(inflater, container, false);


        binding.categoryDescription.setText(data.getCategoryDescription());
        binding.categoryName.setText(data.getCategoryName());
        Glide.with(getContext()).load(data.getCategoryImage()).into(binding.categoryImage);












        View view = binding.getRoot();


        return view;
    }
}
