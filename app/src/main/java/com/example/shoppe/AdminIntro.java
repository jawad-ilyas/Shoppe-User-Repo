package com.example.shoppe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shoppe.AmindFolder.BrandPageActivityAdmin;
import com.example.shoppe.AmindFolder.CategoryPageActivityAdmin;
import com.example.shoppe.AmindFolder.ProductPageActivityAdmin;
import com.example.shoppe.databinding.ActivityAdminIntroBinding;

public class AdminIntro extends AppCompatActivity {

    ActivityAdminIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.moveToBrandPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminIntro.this , BrandPageActivityAdmin.class));
                finish();
            }
        });


        binding.moveToCategoryPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminIntro.this , CategoryPageActivityAdmin.class));
                finish();
            }
        });



        binding.moveToProductPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminIntro.this , ProductPageActivityAdmin.class));
                finish();
            }
        });


    }
}