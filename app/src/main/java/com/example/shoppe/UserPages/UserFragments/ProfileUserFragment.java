package com.example.shoppe.UserPages.UserFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.MainActivity;
import com.example.shoppe.R;
import com.example.shoppe.UserPages.Adapters.ProfileUserAdapter;
import com.example.shoppe.UserPages.UserModel.HomeUserModel;
import com.example.shoppe.UserPages.UserModel.ProfileUserModel;
import com.example.shoppe.databinding.FragmentProfileUserBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileUserFragment extends Fragment {

    String CheckoutDetails = "CheckoutDetails/";
    ProfileUserAdapter adapter;

    FragmentProfileUserBinding binding;
    public ProfileUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {






        binding = FragmentProfileUserBinding.inflate(inflater, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String email = preferences.getString("email", "no email id");
        String userId = preferences.getString("userId", "no userIid");
        GlobalFunctions globalFunctions = new GlobalFunctions();
        adapter = globalFunctions.fetchDataFromFireBase(binding.profileRecycerView,CheckoutDetails + userId , getContext()
        , ProfileUserModel.class , ProfileUserAdapter.class , false);

        binding.logout.setOnClickListener(v -> {


            AlertDialog.Builder logoutAlert = new AlertDialog.Builder(getContext());
            logoutAlert.setTitle("Log Out ");
            logoutAlert.setMessage("Are Your Sure You want to Logout");
            logoutAlert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(intent);
                }
            }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        logoutAlert.show();



        });
        return binding.getRoot();
    }
    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.startListening();
        else
            Toast.makeText(getContext(), "Adapter is null", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter !=null)
            adapter.stopListening();
        else
            Toast.makeText(getContext(), "Adapter is null", Toast.LENGTH_SHORT).show();
    }
}