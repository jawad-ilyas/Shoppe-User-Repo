package com.example.shoppe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shoppe.databinding.ActivityPasswordBinding;

public class Password extends AppCompatActivity {


    ActivityPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.moveToPasswordRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Password.this, paswordRecovery_code.class));
                finish();
            }
        });
        binding.moveToAndimIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Password.this, AdminIntro.class));
                finish();
            }
        });
    }
}