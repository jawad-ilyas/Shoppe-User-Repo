package com.example.shoppe.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.shoppe.R;
import com.example.shoppe.databinding.FragmentHelloCardOnBaordBinding;
import com.example.shoppe.databinding.FragmentShoppingCardOnBoardBinding;


public class HelloCardOnBaordFrag extends Fragment {



    FragmentHelloCardOnBaordBinding binding;
    public HelloCardOnBaordFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHelloCardOnBaordBinding.inflate(inflater,container , false);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        binding.helloHeading.startAnimation(fadeInAnimation);
        Animation fadeInFromBottomAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_from_bottom);
        binding.helloSubHeading.startAnimation(fadeInFromBottomAnimation);

        return  binding.getRoot();
    }
}