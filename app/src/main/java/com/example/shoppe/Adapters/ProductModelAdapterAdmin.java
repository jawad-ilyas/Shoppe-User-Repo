package com.example.shoppe.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppe.R;
import com.example.shoppe.databinding.SingleProductDesignBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProductModelAdapterAdmin extends FirebaseRecyclerAdapter<ProductModelAdmin, ProductModelAdapterAdmin.MyProductViewHodler> {



    public ProductModelAdapterAdmin(@NonNull FirebaseRecyclerOptions<ProductModelAdmin> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyProductViewHodler holder, int position, @NonNull ProductModelAdmin model) {


        holder.binding.productName.setText(model.getProductName());
        holder.binding.productPrice.setText(model.getProductPrice());
        holder.binding.productDescription.setText(model.getProductDescription());
        // Load image using Glide
        Glide.with(holder.itemView.getContext())
                .load(model.getProductImage())  // Assuming that model.getImagePath() returns the URL or URI of the image
                .placeholder(R.drawable.admin_img)  // You can use a placeholder drawable while the image is loading
                .error(R.drawable.upload_image_avatar)  // You can use an error drawable if Glide fails to load the image
                .into(holder.binding.productImage);



    }

    @NonNull
    @Override
    public MyProductViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product_design, parent, false);
      return new MyProductViewHodler(view);
    }

    public class MyProductViewHodler extends RecyclerView.ViewHolder {

        SingleProductDesignBinding binding;
        public MyProductViewHodler(@NonNull View itemView) {
            super(itemView);

            binding = SingleProductDesignBinding.bind(itemView);
        }
    }
}
