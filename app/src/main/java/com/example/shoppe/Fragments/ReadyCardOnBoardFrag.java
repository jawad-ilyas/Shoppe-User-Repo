package com.example.shoppe.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.shoppe.CreateAccount;
import com.example.shoppe.R;
import com.example.shoppe.databinding.FragmentReadyCardOnBoardBinding;


public class ReadyCardOnBoardFrag extends Fragment {
    private FragmentReadyCardOnBoardBinding binding;


    public ReadyCardOnBoardFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReadyCardOnBoardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        binding.readyHeading.startAnimation(fadeInAnimation);
        Animation fadeInFromBottomAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_from_bottom);
        binding.readySubText.startAnimation(fadeInFromBottomAnimation);
        binding.moveToCreateAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateAccount.class));

            }
        });
        return view;
    }
}