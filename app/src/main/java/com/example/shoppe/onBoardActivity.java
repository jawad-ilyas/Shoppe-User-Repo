package com.example.shoppe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.shoppe.Adapters.OnBoardAdapter;
import com.example.shoppe.databinding.ActivityOnBoardBinding;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class onBoardActivity extends AppCompatActivity {


    ActivityOnBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        FragmentManager fragmentManager = getSupportFragmentManager();
        OnBoardAdapter adapter = new OnBoardAdapter(fragmentManager , getLifecycle());
        binding.firstTimeOnBoardPager.setAdapter(adapter);

        WormDotsIndicator wormDotsIndicator = findViewById(R.id.worm_dots_indicator);
        wormDotsIndicator.attachTo(binding.firstTimeOnBoardPager);
    }
}