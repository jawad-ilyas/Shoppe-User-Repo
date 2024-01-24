package com.example.shoppe.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.Interface.ItemClickedListener;
import com.example.shoppe.Models.BrandModelAdmin;
import com.example.shoppe.Models.CategoryModelAdmin;
import com.example.shoppe.Models.ProductModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.databinding.CategorySingleLayoutFragmentAdminBinding;
import com.example.shoppe.databinding.DialogBoxLayoutBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.HashMap;

public class CategoryAdapterAdmin extends FirebaseRecyclerAdapter<CategoryModelAdmin,
        CategoryAdapterAdmin.CategoryViewHolder> implements ItemClickedListener {



    public void categoryItemSelected(CategoryModelAdmin data){};


    ItemClickedListener clickedListener;
    public CategoryAdapterAdmin(@NonNull FirebaseRecyclerOptions<CategoryModelAdmin> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull CategoryModelAdmin model) {
        holder.binding.categoryDescription.setText(model.getCategoryDescription());
        holder.binding.categoryName.setText(model.getCategoryName());
        Glide.with(holder.itemView.getContext()).
                load(model.getCategoryImage()).into(holder.binding.categoryImage);


        Context context = holder.binding.categoryDescription.getContext();
        clickedListener = (ItemClickedListener) context;

        // clicked listener
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Item clicked on Category", Toast.LENGTH_SHORT).show();

            if (clickedListener != null) {

                clickedListener.categoryItemSelected(new CategoryModelAdmin(model.getCategoryName(),
                        model.getCategoryDescription(),
                        model.getCategoryImage()));
            }
        });
        holder.binding.update.setOnClickListener( v -> {


            GlobalFunctions globalFunctions = new GlobalFunctions();

            AlertDialog.Builder updateCategoryDialog = new AlertDialog.Builder(context);
            DialogBoxLayoutBinding dialogBinding = DialogBoxLayoutBinding.inflate(LayoutInflater.from(context));
            updateCategoryDialog.setView(dialogBinding.getRoot());
            updateCategoryDialog.setTitle("Update The Category");
            updateCategoryDialog.setIcon(R.drawable.edit_3_svgrepo_com);
            dialogBinding.dialogName.setText(model.getCategoryName());
            dialogBinding.dialogDescription.setText(model.getCategoryDescription());
            Glide.with(context).load(model.getCategoryImage()).into(dialogBinding.dialogImage);

            updateCategoryDialog.setPositiveButton("Update" , (((dialog, which) -> {

                String categoryName = globalFunctions.FieldText(dialogBinding.dialogName);
                String categoryDescription = globalFunctions.FieldText(dialogBinding.dialogDescription);
                HashMap<String , Object>data = new HashMap<>();
                data.put("categoryName" , categoryName);
                data.put("categoryDescription" , categoryDescription);

                getRef(position).updateChildren(data).addOnSuccessListener(unused -> {
                    Toast.makeText(context, "Category updated", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Category not updated", Toast.LENGTH_SHORT).show();

                });

            }))).setNegativeButton("Cancel" , (((dialog, which) -> {
            })));

            updateCategoryDialog.show();
        });

        holder.binding.delete.setOnClickListener(v -> {

            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
            deleteDialog.setTitle("Delete Category");
            deleteDialog.setMessage("Are You Sure");
            deleteDialog.setIcon(R.drawable.delete_svgrepo_com);
            deleteDialog.setPositiveButton("Delete", (dialog, which) -> {

                getRef(position).removeValue();
            }).setNegativeButton("Cancel",((dialog, which) -> {

            }));
            deleteDialog.show();
        });
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
