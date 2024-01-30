package com.example.shoppe.UserPages.Adapters;

import android.content.Context;
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
import com.example.shoppe.UserPages.UserModel.ShopUserModel;
import com.example.shoppe.databinding.SingleCardProductBinding;
import com.example.shoppe.databinding.SingleUserProductDesignBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Arrays;

public class ShopUserAdapter extends FirebaseRecyclerAdapter<ShopUserModel,
        ShopUserAdapter.UserHomeViewHolder>  {






    ItemClickedListener itemClicked ;
    public ShopUserAdapter(@NonNull FirebaseRecyclerOptions<ShopUserModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull UserHomeViewHolder holder, int position, @NonNull ShopUserModel model) {
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





    }

    @NonNull
    @Override
    public UserHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card_product, parent, false);
        return new UserHomeViewHolder(view);
    }

    public class UserHomeViewHolder extends RecyclerView.ViewHolder{
        SingleCardProductBinding binding;
        public UserHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleCardProductBinding.bind(itemView);
        }
    }
}
