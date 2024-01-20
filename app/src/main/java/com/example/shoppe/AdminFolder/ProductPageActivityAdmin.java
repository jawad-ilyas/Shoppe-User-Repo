package com.example.shoppe.AdminFolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppe.Models.ProductModelAdmin;
import com.example.shoppe.databinding.ActivityProductPageAdminBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ProductPageActivityAdmin extends AppCompatActivity {

    final Integer  PICK_IMAGE_REQUEST_CODE = 1;
    Uri imagePath ;
    ActivityProductPageAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductPageAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.AddProductIntoDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String productName = FieldText(binding.productName);
               String productDescription = FieldText(binding.productDescription);
               String productPrice = FieldText(binding.productPrice);
               
               if(productName.isEmpty())
               {
                   binding.productName.setError("Product Name is Required");
               } else if (productDescription.isEmpty()  ) {
                   binding.productDescription.setError("Product Description is Required");
               } else if (productPrice.isEmpty()) {
                   binding.productPrice.setError("Product Price is Required");
               } else if (imagePath ==null) {
                   Toast.makeText(ProductPageActivityAdmin.this, "Please Select Image", Toast.LENGTH_SHORT).show();
               } else
               {


                   FirebaseStorage storage = FirebaseStorage.getInstance();
                   String imageName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + ".jpg";

                   StorageReference reference = storage.getReference().child("productsImg/").child(imageName);

                   reference.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                                 ProductModelAdmin productModelAdmin = new
                                         ProductModelAdmin(productName, productDescription ,
                                         productPrice, uri.toString());

                                 FirebaseDatabase db = FirebaseDatabase.getInstance();
                                 DatabaseReference root = db.getReference().child("productsDetail/");



                                 String productName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() ;
                                 root.child(productName).setValue(productModelAdmin);



                                 FieldText(binding.productName);
                                    FieldText(binding.productDescription);
                                    FieldText(binding.productPrice);

                                 Toast.makeText(ProductPageActivityAdmin.this, "Product is Added", Toast.LENGTH_SHORT).show();

                             }
                         });
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(ProductPageActivityAdmin.this, "Image is Not Uploaded", Toast.LENGTH_SHORT).show();
                       }
                   });

               }

               
               
               
            }
        });
        binding.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*"); // Set the type of data to pick (in this case, images)

// Start the activity for result
                startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
            }
        });
    }
    
    String FieldText(EditText editText)
    {
        return editText.getText().toString().trim();
    }
    void EmptyFieldText(EditText editText)
    {
       editText.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == PICK_IMAGE_REQUEST_CODE &&  resultCode == RESULT_OK)
        {
            imagePath = data.getData();
            // Use Glide to load and set the image in your ImageView
            Glide.with(this)
                    .load(imagePath)
                    .into(binding.productImage);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}