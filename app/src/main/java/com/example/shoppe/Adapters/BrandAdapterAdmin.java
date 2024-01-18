package com.example.shoppe.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppe.Models.BrandModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.databinding.BrandSingleLayoutBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

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
