package com.example.shoppe.UserPages.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppe.Interface.ItemClickedListener;
import com.example.shoppe.R;
import com.example.shoppe.UserPages.UserModel.HomeUserModel;
import com.example.shoppe.databinding.SingleUserProductDesignBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Arrays;

public class HomeUserAdapter extends FirebaseRecyclerAdapter<HomeUserModel,
        HomeUserAdapter.UserHomeViewHolder> implements ItemClickedListener {

    // create a interface because we want to add click listener on tab


    @Override
    public void productItemSelectedUser(HomeUserModel data) {
        ItemClickedListener.super.productItemSelectedUser(data);
    }

    ItemClickedListener itemClicked ;
    public HomeUserAdapter(@NonNull FirebaseRecyclerOptions<HomeUserModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull UserHomeViewHolder holder, int position, @NonNull HomeUserModel model) {
        holder.binding.productName.setText(model.getProductName());
        holder.binding.productPrice.setText(model.getProductPrice());
        String fullDescription = model.getProductDescription();
        String[] words = fullDescription.split("\\s+"); // Split the description into words

        // Take the first 5 words or less
        int wordsToShow = Math.min(5, words.length);
        String limitedDescription = String.join(" ", Arrays.copyOfRange(words, 0, wordsToShow));

        holder.binding.productDescription.setText(limitedDescription);


        // Load image using Glide
        Glide.with(holder.itemView.getContext())
                .load(model.getProductImage())  // Assuming that model.getImagePath() returns the URL or URI of the image
                .placeholder(R.drawable.admin_img)  // You can use a placeholder drawable while the image is loading
                .error(R.drawable.upload_image_avatar)  // You can use an error drawable if Glide fails to load the image
                .into(holder.binding.productImage);
        Context customContext = holder.binding.productDescription.getContext();
        itemClicked = (ItemClickedListener )customContext;

        holder.itemView.setOnClickListener( v -> {
            itemClicked.productItemSelectedUser(model);
        });

    }

    @NonNull
    @Override
    public UserHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_product_design, parent, false);
        return new UserHomeViewHolder(view);
    }

    public class UserHomeViewHolder extends RecyclerView.ViewHolder{
        SingleUserProductDesignBinding binding;
        public UserHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleUserProductDesignBinding.bind(itemView);
        }
    }
}
