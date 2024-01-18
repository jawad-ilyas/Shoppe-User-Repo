package com.example.shoppe;


import android.content.Context;
import android.net.Uri;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppe.Adapters.BrandAdapterAdmin;
import com.example.shoppe.Models.BrandModelAdmin;
import com.example.shoppe.Models.CategoryModelAdmin;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class GlobalFunctions {

    public GlobalFunctions(){}


    String uniqueName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() ;


    public String FieldText(EditText editText)
    {
        return editText.getText().toString().trim();
    }


    // this function set error or remove the error from the textFields
    public boolean validateField(String fieldValue, EditText fieldLayout, String errorMessage) {
        if (fieldValue.isEmpty()) {
            fieldLayout.setError(errorMessage);
            return false;
        } else {
            // Clear any previous errors if they exist
            fieldLayout.setError(null);
            return true;
        }
    }


    public interface UploadCallback<T> {
        void onSuccess(T data);
        void onFailure(Exception e);
    }

    public <T> void uploadDataIntoFireBase(String name, String description, Uri imageResource,String FolderDetail ,String imageDetail ,   Class<T> modelClass, UploadCallback<T> callback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = db.getReference().child(FolderDetail).child(uniqueName);

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReference().child(imageDetail);

        storageReference.putFile(imageResource).addOnSuccessListener(taskSnapshot -> {
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                try {
                    // Use reflection to create an instance of the specified model class
                    T modelInstance = modelClass.getDeclaredConstructor(String.class, String.class, String.class)
                            .newInstance(name, description, uri.toString());

                    dbReference.setValue(modelInstance);
                    callback.onSuccess(modelInstance);
                } catch (Exception e) {
                    // Handle reflection or instantiation errors
                    callback.onFailure(e);
                }
            });
        }).addOnFailureListener(e -> {
            // Execute the onFailure callback with the exception
            callback.onFailure(e);
        });
    }

    public <T , A> A  fetchDataFromFireBase(RecyclerView recyclerView , String firebasePath  , Context context , Class<T> modelClass ,Class<A> adapterClass ){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(firebasePath);


        FirebaseRecyclerOptions<T> options =
                new FirebaseRecyclerOptions.Builder<T>()
                        .setQuery(query, modelClass)
                        .build();

        try {
            // Use reflection to create an instance of the adapter
            A adapter = adapterClass.getConstructor(FirebaseRecyclerOptions.class)
                    .newInstance(options);

            recyclerView.setAdapter((RecyclerView.Adapter) adapter);

            ((RecyclerView.Adapter<?>) adapter).notifyDataSetChanged();
            return adapter;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception accordingly
        }

        return null;
    };


}
