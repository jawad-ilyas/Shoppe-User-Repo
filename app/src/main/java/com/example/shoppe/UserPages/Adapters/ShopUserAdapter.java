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
import com.example.shoppe.Models.CategoryModelAdmin;
import com.example.shoppe.Models.ProductModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.UserPages.UserModel.HomeUserModel;
import com.example.shoppe.UserPages.UserModel.ShopUserModel;
import com.example.shoppe.databinding.SingleCardProductBinding;
import com.example.shoppe.databinding.SingleUserProductDesignBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;

public class ShopUserAdapter extends FirebaseRecyclerAdapter<ShopUserModel,
        ShopUserAdapter.UserHomeViewHolder> implements ItemClickedListener  {

    Context customContext;
    int totalPrice = 0;

    public void TotalPriceIntoCart(int price){}
    public ItemClickedListener clickedListener;
    ItemClickedListener itemClicked;
    public ShopUserAdapter(@NonNull FirebaseRecyclerOptions<ShopUserModel> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull UserHomeViewHolder holder, int position, @NonNull ShopUserModel model) {
        holder.binding.productName.setText(model.getProductName());
        holder.binding.productPrice.setText(model.getProductPrice());
        holder.binding.productQuantity.setText(  String.valueOf(   model.getProductQuantity()));
        // minus the quantity
        holder.binding.minusButton.setOnClickListener(v -> {
            if(model.getProductQuantity() == 1)
            {
                Toast.makeText(v.getContext(), "We cannot more minus quantity" , Toast.LENGTH_SHORT).show();
            }
            else {
                totalPrice = totalPrice - Integer.parseInt(model.getProductPrice())
                -Integer.parseInt(model.getProductPrice())
                ;
                HashMap<String , Object> quantityChanger = new HashMap<>();
                quantityChanger.put("productQuantity",model.getProductQuantity()-1);
                getRef(position).updateChildren(quantityChanger);
            }





        });

        // implement from increase category
        holder.binding.plusButton.setOnClickListener(v -> {
                HashMap<String , Object> quantityChanger = new HashMap<>();
                quantityChanger.put("productQuantity",model.getProductQuantity()+1);
                getRef(position).updateChildren(quantityChanger);




        });

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
         customContext = holder.binding.productDescription.getContext();



//        holder.itemView.setOnClickListener(v -> {
//            Toast.makeText(customContext, "item clicked" , Toast.LENGTH_SHORT).show();
//            if(clickedListener!=null){
//                clickedListener.TotalPriceIntoCart(model.getProductQuantity());
//                Toast.makeText(customContext, "item clicked is not null" , Toast.LENGTH_SHORT).show();
//
//            }
//            else{
//                Toast.makeText(customContext, "item is null "  , Toast.LENGTH_SHORT).show();
//
//            }
//
//        });

        TotalPriceIntoCartTosend(Integer.parseInt(model.getProductPrice()));

    }
    void TotalPriceIntoCartTosend(int price)
    {
        totalPrice = totalPrice + price;
//        Toast.makeText(customContext, "price" + totalPrice , Toast.LENGTH_SHORT).show();
        if (clickedListener != null) {
//            Toast.makeText(customContext, "not not not not not not not"  , Toast.LENGTH_LONG).show();

            clickedListener.TotalPriceIntoCart(totalPrice);
        }else {
            Toast.makeText(customContext, "failed failed"  , Toast.LENGTH_LONG).show();

        }


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
