package com.example.shoppe.UserPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.shoppe.R;
import com.example.shoppe.UserPages.UserFragments.HomeUserFragement;
import com.example.shoppe.UserPages.UserFragments.ProfileUserFragment;
import com.example.shoppe.UserPages.UserFragments.ShopUserFragment;
import com.example.shoppe.databinding.ActivityUserIntroBinding;
import com.google.android.material.navigation.NavigationBarView;

public class UserIntro extends AppCompatActivity {

    ActivityUserIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        replaceFragment(new HomeUserFragement());
        binding.userBottomNaviagtion.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemListener = item.getItemId();

                if(itemListener == R.id.HomeUserIcon)
                {
                    replaceFragment(new HomeUserFragement());
                    return true;
                }else if(itemListener == R.id.StoreIcon)
                {
                    replaceFragment(new ShopUserFragment());
                    return true;
                }
                else if(itemListener == R.id.profileIcon)
                {
                    replaceFragment(new ProfileUserFragment());
                    return  true;
                }
                return false;
            }
        });


//        private void replaceFragment(Fragment fragment) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(binding.layoutContainerForFragments.getId(), fragment)
//                    .commit();
//        }

    }
    void replaceFragment(Fragment fragment){

        getSupportFragmentManager().beginTransaction().replace(binding.userContainer.getId() , fragment).commit();
    }
}