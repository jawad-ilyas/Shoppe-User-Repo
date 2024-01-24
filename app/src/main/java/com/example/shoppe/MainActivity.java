package com.example.shoppe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.shoppe.AdminFolder.HomeAdminFragment;
import com.example.shoppe.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences preferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String email = preferences.getString("email" , "");
        String userId = preferences.getString("userId" , "");


        if(email != "")
        {
            startActivity(new Intent(MainActivity.this , AdminIntro.class));
        }

        binding.moveToCreateAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        // move to create acount screen
            startActivity(new Intent(MainActivity.this , CreateAccount.class));
            finish();
            }
        });
        binding.moveToLoginPage.setOnClickListener( v -> {
            startActivity(new Intent(MainActivity.this , LoginActivity.class));
            finish();
        });
    }
}