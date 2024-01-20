package com.example.shoppe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.shoppe.Adapters.ProductModelAdapterAdmin;
import com.example.shoppe.AdminFolder.BrandFragmentAdmin;
import com.example.shoppe.AdminFolder.CategoryFragmentAdmin;
import com.example.shoppe.AdminFolder.HomeAdminFragment;
import com.example.shoppe.AdminFolder.ProductFragmentAdmin;
import com.example.shoppe.databinding.ActivityAdminIntroBinding;
import com.google.android.material.navigation.NavigationBarView;

public class AdminIntro extends AppCompatActivity {

    ProductModelAdapterAdmin adapter;
    ActivityAdminIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        replaceFragment(new HomeAdminFragment());
        binding.bottomNaviationAdmin.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int itemListener = item.getItemId();

                if (itemListener == R.id.productIcon) {
                    replaceFragment(new ProductFragmentAdmin());
                    return true;
                } else if (itemListener == R.id.categoryIcon)
                {
                    replaceFragment(new CategoryFragmentAdmin());
                    return true;
                }
                else if (itemListener == R.id.HomeIcon)
                {
                    replaceFragment(new HomeAdminFragment());
                    return true;
                }
                else if(itemListener == R.id.brandIcon)
                {
                    replaceFragment(new BrandFragmentAdmin());
                    return true;
                }
                return false;
            }
        });


    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(binding.layoutContainerForFragments.getId(), fragment)
                .commit();
    }






}