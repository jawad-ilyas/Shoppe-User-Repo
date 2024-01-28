package com.example.shoppe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shoppe.UserPages.UserIntro;
import com.example.shoppe.databinding.ActivityPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                GlobalFunctions globalFunctions = new GlobalFunctions();
                String password = globalFunctions.FieldText(binding.password);

                  if(!globalFunctions.validateField(password, binding.password, "Password is required")) {
                    return;
                    }
                  else {


                      FirebaseAuth auth = FirebaseAuth.getInstance();
                      String email = getIntent().getStringExtra("email");
                      Toast.makeText(Password.this, ""+ email + password, Toast.LENGTH_SHORT).show();
                      auth.signInWithEmailAndPassword(email , password).addOnCompleteListener( Password.this,new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              if (task.isSuccessful()) {
                                  // Sign up success, update UI with the signed-in user's information
                                  FirebaseUser user = auth.getCurrentUser();
                                  Intent intent = new Intent(Password.this , UserIntro.class);
                                  intent.putExtra("email", user.getEmail());
                                  intent.putExtra("userId", user.getUid());
                                  SharedPreferences preferences = getSharedPreferences( "loginInfo" , MODE_PRIVATE);
                                  SharedPreferences.Editor editor = preferences.edit();
                                  editor.putString("email", user.getEmail());
                                  editor.putString("userId", user.getUid());
                                  editor.apply();
                                  startActivity(intent);
                                  finish();
                              } else {
                                  // If sign up fails, display a message to the user.
                                  Toast.makeText(Password.this, "Failed to create user account: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
//                      startActivity(new Intent(Password.this, AdminIntro.class));
//                      finish();
                  }
            }
        });
    }
}