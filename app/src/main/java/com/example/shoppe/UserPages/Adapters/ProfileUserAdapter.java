package com.example.shoppe.UserPages.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppe.Interface.ItemClickedListener;
import com.example.shoppe.R;
import com.example.shoppe.UserPages.UserModel.HomeUserModel;
import com.example.shoppe.UserPages.UserModel.ProductDetails;
import com.example.shoppe.UserPages.UserModel.ProfileUserModel;
import com.example.shoppe.databinding.SingelOrderIdBinding;
import com.example.shoppe.databinding.SingleUserProductDesignBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileUserAdapter extends FirebaseRecyclerAdapter<ProfileUserModel,
        ProfileUserAdapter.UserHomeViewHolder>  {




    public ProfileUserAdapter(@NonNull FirebaseRecyclerOptions<ProfileUserModel> options) {
        super(options);
    }



//    @Override
//    protected void onBindViewHolder(@NonNull UserHomeViewHolder holder, int position, @NonNull ProfileUserModel model) {
//        Log.d("model", String.valueOf(model));
//
//        // Get the key of the current "ProfileUserModel" node (assuming it is an order ID)
//        String orderId = getRef(position).getKey();
//        holder.binding.tackingId.setText(orderId);
//
//        // Get the reference to the order node
//        DatabaseReference orderReference = getRef(position).getRef();
//
//        // Attach a ValueEventListener to retrieve product IDs under the order
//        orderReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // Iterate through product IDs and fetch corresponding product details
//                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
//                    String productId = productSnapshot.getKey();
//                    Log.d("productSnapshot.getKey()", productSnapshot.getKey());
//
//                    // Accessing one level deeper
//
//                    DataSnapshot productDetailsSnapshot = productSnapshot.child(productId);
//                    String productDescription = productDetailsSnapshot.child("productDescription").getValue(String.class);
//                    String productImage = productDetailsSnapshot.child("productImage").getValue(String.class);
//                    String productName = productDetailsSnapshot.child("productName").getValue(String.class);
//                    String productPrice = productDetailsSnapshot.child("productPrice").getValue(String.class);
////                    Log.d("productDescription" ,productDescription);
////                    Log.d("productName" ,productName);
////                    Log.d("productPrice" ,productPrice);
//                    holder.binding.productDescription.setText(productDescription);
//
//                    holder.binding.productName.setText(productName);
//                    holder.binding.productPrice.setText(productPrice);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("Firebase", "Error fetching products: " + databaseError.getMessage());
//            }
//        });
//
//    }


    @Override
    protected void onBindViewHolder(@NonNull UserHomeViewHolder holder, int position, @NonNull ProfileUserModel model) {
        Log.d("model", String.valueOf(model));

        // Get the key of the current "ProfileUserModel" node (assuming it is an order ID)
        String orderId = getRef(position).getKey();
        holder.binding.tackingId.setText(orderId);

        // Get the reference to the order node
        DatabaseReference orderReference = getRef(position).getRef();

        // Create a list to store product details
        List<ProductDetails> productList = new ArrayList<>();

        // Attach a ValueEventListener to retrieve product IDs under the order
        orderReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    // Accessing one level deeper to get product details
                    String productDescription = productSnapshot.child("productDescription").getValue(String.class);
                    String productImage = productSnapshot.child("productImage").getValue(String.class);
                    String productName = productSnapshot.child("productName").getValue(String.class);
                    String productPrice = productSnapshot.child("productPrice").getValue(String.class);

                    // Create a ProductDetails object and add it to the list
                    ProductDetails productDetails = new ProductDetails(productName, productDescription, productPrice, productImage);
                    productList.add(productDetails);
                }

                // Update UI with the product details
                updateUI(holder, productList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error fetching products: " + databaseError.getMessage());
            }
        });
    }

    // Helper method to update UI with the product details
    private void updateUI(UserHomeViewHolder holder, List<ProductDetails> productList) {
        for (ProductDetails productDetails : productList) {
            // Inflate a new layout for each product
            View productView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.single_user_product_design_design, null);
            SingleUserProductDesignBinding productBinding = SingleUserProductDesignBinding.bind(productView);

            // Set the product details in the inflated layout
            productBinding.productDescription.setText(productDetails.getProductDescription());
            productBinding.productName.setText(productDetails.getProductName());
            productBinding.productPrice.setText(productDetails.getProductPrice());

            // Load image using Glide
            Glide.with(holder.itemView.getContext())
                    .load(productDetails.getProductImage())  // Assuming that getProductImage() returns the URL or URI of the image
                    .placeholder(R.drawable.admin_img)  // You can use a placeholder drawable while the image is loading
                    .error(R.drawable.upload_image_avatar)  // You can use an error drawable if Glide fails to load the image
                    .into(productBinding.productImage);

            // Add the inflated layout to the main layout
            holder.binding.productContainer.addView(productView);
        }
    }


    @NonNull
    @Override
    public UserHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singel_order_id, parent, false);
        return new UserHomeViewHolder(view);
    }

    public class UserHomeViewHolder extends RecyclerView.ViewHolder{

        SingelOrderIdBinding binding;


        public UserHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingelOrderIdBinding.bind(itemView);
        }
    }
}
