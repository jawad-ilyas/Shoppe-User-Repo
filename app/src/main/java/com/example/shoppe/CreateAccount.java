package com.example.shoppe;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppe.Models.AdminData;
import com.example.shoppe.databinding.ActivityCreateAccountBinding;
import com.example.shoppe.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateAccount extends AppCompatActivity {


    private FirebaseAuth mAuth;
    Uri imageResource;
    FirebaseUser user;

    ActivityCreateAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ActivityResultLauncher<String> mGetContext = registerForActivityResult(new ActivityResultContracts.GetContent(),

                    uri-> {
                        Glide.with(this).load(uri).into(binding.adminImage);
                        imageResource = uri;
                    });

        // admin image
        binding.adminImage.setOnClickListener( v1 -> {
            mGetContext.launch("image/*");

        });

        binding.moveToLoginActiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // check data is valid or not

                mAuth = FirebaseAuth.getInstance();
                GlobalFunctions globalFunctions = new GlobalFunctions();
                String adminName = globalFunctions.FieldText(binding.adminName);
                String adminPhone = globalFunctions.FieldText(binding.adminPhone);

                String email = globalFunctions.FieldText(binding.email);
                String password = globalFunctions.FieldText(binding.password);
                String conformPassword = globalFunctions.FieldText(binding.conformPassword);
                if(!globalFunctions.validateField(adminName, binding.adminName, "Name is required")){
                    return;
                } else if (!globalFunctions.validateField(adminPhone, binding.adminPhone, "Phone is required")) {
                    return;
                } else if (!globalFunctions.validateField(email, binding.email, "Email is required")) {
                    return;
                } else if (!globalFunctions.validateField(password, binding.password, "Password is required")) {
                    return;
                } else if (!globalFunctions.validateField(conformPassword, binding.conformPassword, "Confirm password is required")) {
                    return;
                }else if(imageResource == null){
                    Toast.makeText(CreateAccount.this, "image is required", Toast.LENGTH_SHORT).show();
                }
                else {



                    if (password.equals(conformPassword)) {


                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign up success, update UI with the signed-in user's information
                                            user = mAuth.getCurrentUser();
                                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                                           DatabaseReference adminInfo =db.getReference("AdminInfo/");

                                            FirebaseStorage storage = FirebaseStorage.getInstance();
                                            StorageReference reference = storage.getReference("AdminImages/"+user.getUid());

                                            reference.putFile(imageResource).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {

                                                            AdminData data= new AdminData(adminName, adminPhone , email , password , uri.toString());
                                                            adminInfo.child(user.getUid()).setValue(data);
                                                            SharedPreferences preferences = getSharedPreferences( "loginInfo" , MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = preferences.edit();
                                                            editor.putString("email", user.getEmail());
                                                            editor.putString("userId", user.getUid());
                                                            editor.apply();
                                                            startActivity(new Intent(CreateAccount.this , AdminIntro.class));
                                                            finish();
                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("CreateAccount", "Failed to store admin information: " + task.getException().getMessage());

                                                }
                                            });
                                        } else {
                                            // If sign up fails, display a message to the user.
                                            Toast.makeText(CreateAccount.this, "Failed to create user account: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(CreateAccount.this, "Password or confirm password is not matched", Toast.LENGTH_SHORT).show();
                    }

                }









//                // move to create acount screen
//                startActivity(new Intent(CreateAccount.this , LoginActivity.class));
//                finish();
            }
        });
    }
}