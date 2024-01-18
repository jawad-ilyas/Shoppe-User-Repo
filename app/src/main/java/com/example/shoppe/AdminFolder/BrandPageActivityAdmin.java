package com.example.shoppe.AdminFolder;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shoppe.Adapters.BrandAdapterAdmin;
import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.Models.BrandModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.databinding.ActivityBrandPageAdminBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class BrandPageActivityAdmin extends AppCompatActivity {
    FirebaseRecyclerAdapter<BrandModelAdmin , BrandAdapterAdmin.myBrandViewHolder> adapter;

    final String brandDetail = "brandDetail/";
    final String brandImg = "brandImg/";
    Uri imageResoucre;
    ActivityBrandPageAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrandPageAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // we create object there becuase we want to access globaly function
        GlobalFunctions globalFunctions = new GlobalFunctions();



        // logic for collect image from the user and same into uri

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),

                uri ->{
                        binding.brandImage.setImageURI(uri);
                        imageResoucre = uri;
                });


        // listener for images listener

        binding.brandImage.setOnClickListener(v->{
            mGetContent.launch("image/*");
        });







        // clicked listener for add brand
        binding.AddBrandIntoDataBase.setOnClickListener(v ->    {


            String brandName = globalFunctions.FieldText(binding.brandName);
            String brandDescription = globalFunctions.FieldText(binding.brandDescription);

            if(!globalFunctions.validateField(brandName , binding.brandName , "Name is Required"))
            {
                return;
            }
            else if(!globalFunctions.validateField(brandDescription , binding.brandDescription , "Description is Required"))
            {
                return;
            }
            else if(imageResoucre == null){
                Toast.makeText(this, "image Fields are required ", Toast.LENGTH_SHORT).show();
            }
            else {
                globalFunctions.uploadDataIntoFireBase(brandName, brandDescription, imageResoucre,brandDetail  ,
                        brandImg, BrandModelAdmin.class, new GlobalFunctions.UploadCallback<BrandModelAdmin>() {
                    @Override
                    public void onSuccess(BrandModelAdmin data) {
                        // Handle success with CategoryModelAdmin data
                        Toast.makeText(getApplicationContext(), "data upload " + data , Toast.LENGTH_SHORT).show();;
                        if (adapter != null) {
                            adapter.startListening();
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {
                        // Handle failure
                        Toast.makeText(getApplicationContext(), "data upload failed " + e , Toast.LENGTH_SHORT).show();;

                    }
                });

                startActivity(new Intent(BrandPageActivityAdmin.this , BrandFragmentAdmin.class));

                finish();


            }

        });

    }
}