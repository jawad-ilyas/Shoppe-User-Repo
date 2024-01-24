package com.example.shoppe.AdminFolder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppe.MainActivity;
import com.example.shoppe.R;
import com.example.shoppe.databinding.FragmentHomeAdminBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeAdminFragment extends Fragment {


    public HomeAdminFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHomeAdminBinding binding;
        binding = FragmentHomeAdminBinding.inflate(inflater , container , false);

        SharedPreferences preferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String email = preferences.getString("email" , "");
        String userId = preferences.getString("userId" , "");

        if(userId == "")
        {

            startActivity(new Intent(getContext() , MainActivity.class));
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser!=null)
        {
//            Toast.makeText(getContext(), "user is find", Toast.LENGTH_SHORT).show();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("AdminInfo/").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
//                        Toast.makeText(getContext(), "snap short is find", Toast.LENGTH_SHORT).show();

                        String username = snapshot.child("adminName").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);

                        binding.adminName.setText(username);
                        binding.user.setText(email);
                        String adminImageURL = snapshot.child("adminImage").getValue(String.class);

                        binding.adminName.setText(username);
                        binding.user.setText(email);

                        // Load admin image using Glide
                        Glide.with(getContext()).load(adminImageURL).into(binding.adminLoginImage);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        binding.moveToBrandPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BrandPageActivityAdmin.class));

            }
        });

        binding.exit.setOnClickListener( v -> {

            AlertDialog.Builder conformMessage = new AlertDialog.Builder(getContext());
            conformMessage.setMessage("Are you Sure Your want to exit ");
            conformMessage.setPositiveButton("Log Out" , (dialog, which) -> {


                SharedPreferences.Editor clearEditor = preferences.edit();
                clearEditor.clear();
                clearEditor.apply();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), MainActivity.class));

            }).setNegativeButton("Cancel" , (dialog, which) -> {

            });
            conformMessage.show();

        });

        binding.moveToCategoryPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , CategoryPageActivityAdmin.class));

            }
        });



        binding.moveToProductPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , ProductPageActivityAdmin.class));

            }
        });


        return binding.getRoot();
    }
}