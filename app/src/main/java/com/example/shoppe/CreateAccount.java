package com.example.shoppe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shoppe.databinding.ActivityCreateAccountBinding;
import com.example.shoppe.databinding.ActivityMainBinding;

public class CreateAccount extends AppCompatActivity {


    ActivityCreateAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.moveToLoginActiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // move to create acount screen
                startActivity(new Intent(CreateAccount.this , LoginActivity.class));
                finish();
            }
        });
    }
}