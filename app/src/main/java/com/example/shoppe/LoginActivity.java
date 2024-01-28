package com.example.shoppe;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shoppe.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        //setup the back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(LoginActivity.this , MainActivity.class));
            }
        };
        getOnBackPressedDispatcher().addCallback(this , callback);

        binding.moveToPassowrdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalFunctions globalFunctions = new GlobalFunctions();
                String email = globalFunctions.FieldText(binding.email);
                if (!globalFunctions.validateField(email, binding.email, "Email is required")) {
                    return;
                }else {
                    Intent intent = new Intent(LoginActivity.this, Password.class);
                    intent.putExtra("email",email );
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}