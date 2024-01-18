package com.example.shoppe.AdminFolder;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppe.Models.CategoryModelAdmin;
import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.databinding.ActivityCategoryPageAdminBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class CategoryPageActivityAdmin extends AppCompatActivity {

ActivityCategoryPageAdminBinding binding;

    Uri imageResource ;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),

            uri -> {
                Glide.with(this).load(uri).into(binding.categoryImage);
                imageResource = uri;
            }
    );


    // on create method start

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryPageAdminBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        GlobalFunctions globalFunctions = new GlobalFunctions();


        // this click listener validated the data , set response
        binding.AddCategoryIntoDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName =  globalFunctions.FieldText(binding.categoryName);
                String categoryDescription =  globalFunctions.FieldText(binding.categoryDescription);

                    if(!globalFunctions.validateField(categoryName , binding.categoryName," Name is Required" ))
                    {
                        return;
                    }
                    else if(!globalFunctions.validateField(categoryDescription , binding.categoryDescription," Description is Required" ))
                    {
                        return;
                    }
                    else{
                            uploadDataToFireBase(categoryName ,categoryDescription );
                    }
            }
        });


        // this clicked listener learn from imageView

        binding.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGetContent.launch("image/*");
            }
        });






    }


    // firebase logic for insertion

    public void uploadDataToFireBase(String categoryName , String categoryDescription){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String categoryImage = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() ;

        DatabaseReference rootNodeDb = db.getReference().child("categoryDetail/").child(categoryImage);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("categoryImg/");


        storageReference.putFile(imageResource).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference.getDownloadUrl( ).addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        CategoryModelAdmin categoryModelAdmin =
                                new CategoryModelAdmin(categoryName , categoryDescription,
                                        uri.toString());
                        String categoryName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() ;
                        rootNodeDb.setValue(categoryModelAdmin);
                        Toast.makeText(CategoryPageActivityAdmin.this, "category is created ", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

}