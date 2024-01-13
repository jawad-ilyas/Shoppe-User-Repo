package com.example.shoppe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shoppe.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.moveToCreateAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        // move to create acount screen
            startActivity(new Intent(MainActivity.this , LoginActivity.class));
            finish();
            }
        });
    }
}