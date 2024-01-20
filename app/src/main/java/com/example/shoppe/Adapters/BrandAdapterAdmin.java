package com.example.shoppe.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.Models.BrandModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.databinding.BrandSingleLayoutBinding;
import com.example.shoppe.databinding.DialogBoxLayoutBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Objects;

public class BrandAdapterAdmin extends FirebaseRecyclerAdapter<BrandModelAdmin , BrandAdapterAdmin.myBrandViewHolder> {


    public  BrandAdapterAdmin(@NonNull FirebaseRecyclerOptions<BrandModelAdmin> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myBrandViewHolder holder, int position, @NonNull BrandModelAdmin model) {

        holder.binding.brandName.setText(model.getBrandName());
        holder.binding.brandDescription.setText(model.getBrandDescription());
        Glide.with(holder.itemView.getContext()).
                load(model.getBrandImage()).into(holder.binding.brandImage);

        Context context =  holder.binding.brandDescription.getContext();


        holder.binding.update.setOnClickListener(v -> {
            AlertDialog.Builder updateDialog = new AlertDialog.Builder(context );
            DialogBoxLayoutBinding dialogBinding = DialogBoxLayoutBinding.inflate(LayoutInflater.from(context));
            updateDialog.setView(dialogBinding.getRoot());

            updateDialog.setIcon(R.drawable.edit_3_svgrepo_com);
            updateDialog.setTitle("Update Brand");
            Glide.with(context)
                    .load(Uri.parse(model.getBrandImage()))
                    .into(dialogBinding.dialogImage);
            dialogBinding.dialogDescription.setText(model.getBrandDescription());

            dialogBinding.dialogName.setText(model.getBrandName());
            updateDialog.setPositiveButton("Update", ((dialog, which) -> {
                    GlobalFunctions globalFunctions = new GlobalFunctions();

                    String dialogName =  globalFunctions.FieldText(dialogBinding.dialogName);
                    String dialogDescription =  globalFunctions.FieldText(dialogBinding.dialogDescription);

                    Toast.makeText(context, dialogName , Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, dialogDescription , Toast.LENGTH_SHORT).show();


                HashMap<String , Object> data = new HashMap<>();

                data.put("brandName" , dialogName);
                data.put("brandDescription" , dialogDescription);

                getRef(position).updateChildren(data).addOnSuccessListener(unused -> {
                    Toast.makeText(context , "update records "  , Toast.LENGTH_SHORT).show();

                }).addOnFailureListener(e -> {
                    Toast.makeText(context , "update records failed"  , Toast.LENGTH_SHORT).show();

                });

            })).setNegativeButton("Cancel", ((dialog, which) -> {



            }));
            updateDialog.show();

        });
        holder.binding.delete.setOnClickListener(v -> {
            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context );
            DialogBoxLayoutBinding dialogBinding = DialogBoxLayoutBinding.inflate(LayoutInflater.from(context));
            deleteDialog.setTitle("Delete Brand");
            deleteDialog.setIcon(R.drawable.delete_svgrepo_com);
            deleteDialog.setMessage("Are You Sure You Want To Delete Brand");

            deleteDialog.setPositiveButton("Delete" ,((dialog, which) -> {

                getRef(position).removeValue().addOnSuccessListener(unused -> {
                    Toast.makeText(context , "Brand are successfully delete "  , Toast.LENGTH_SHORT).show();

                }).addOnFailureListener(e -> {

                });
            })).setNegativeButton("Cancel",(((dialog, which) -> {


            })));
            deleteDialog.show();
        });
    }

    @NonNull
    @Override
    public myBrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view   = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_single_layout , parent , false);
        return  new myBrandViewHolder(view);
    }

    public class myBrandViewHolder extends RecyclerView.ViewHolder{

        BrandSingleLayoutBinding binding;

        public myBrandViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = BrandSingleLayoutBinding.bind(itemView);
        }
    }
}
