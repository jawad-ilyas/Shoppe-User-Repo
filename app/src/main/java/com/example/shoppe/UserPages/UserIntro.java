package com.example.shoppe.UserPages;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.example.shoppe.Interface.ItemClickedListener;
import com.example.shoppe.LoginActivity;
import com.example.shoppe.MainActivity;
import com.example.shoppe.R;
import com.example.shoppe.UserPages.SingleScreen.SingleProductFragment;
import com.example.shoppe.UserPages.UserFragments.HomeUserFragement;
import com.example.shoppe.UserPages.UserFragments.ProfileUserFragment;
import com.example.shoppe.UserPages.UserFragments.ShopUserFragment;
import com.example.shoppe.UserPages.UserModel.HomeUserModel;
import com.example.shoppe.databinding.ActivityUserIntroBinding;
import com.google.android.material.navigation.NavigationBarView;

public class UserIntro extends AppCompatActivity implements ItemClickedListener {

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
                showBottomNavigationView();
                return false;
            }
        });




    }
    void replaceFragment(Fragment fragment){

        getSupportFragmentManager().beginTransaction().replace(binding.userContainer.getId() , fragment).commit();

    }
    void replaceFragmentWithStack(Fragment fragment) {
        hideBottomNavigationView();

        getSupportFragmentManager().beginTransaction()
                .replace(binding.userContainer.getId(), fragment)
                .addToBackStack(null)
                .commit();
    }








    @Override
    public void productItemSelectedUser(String nodeId , HomeUserModel data) {
        replaceFragmentWithStack(new SingleProductFragment(nodeId , data));

    }
    private void hideBottomNavigationView() {
        binding.userBottomNaviagtion.setVisibility(View.GONE);
    }

    private void showBottomNavigationView() {
        binding.userBottomNaviagtion.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        showBottomNavigationView();
        super.onBackPressed();
    }

// To enable the callback

}