package com.example.shoppe.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppe.Models.CategoryModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.databinding.CategorySingleLayoutFragmentAdminBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CategoryAdapterAdmin extends FirebaseRecyclerAdapter<CategoryModelAdmin, CategoryAdapterAdmin.CategoryViewHolder> {



    public CategoryAdapterAdmin(@NonNull FirebaseRecyclerOptions<CategoryModelAdmin> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull CategoryModelAdmin model) {
        holder.binding.categoryDescription.setText(model.getCategoryDescription());
        holder.binding.categoryName.setText(model.getCategoryName());
        Glide.with(holder.itemView.getContext()).
                load(model.getCategoryImage()).into(holder.binding.categoryImage);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_single_layout_fragment_admin , parent, false);
        return new CategoryViewHolder(view);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {


        CategorySingleLayoutFragmentAdminBinding binding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CategorySingleLayoutFragmentAdminBinding.bind(itemView);

        }
    }
}
