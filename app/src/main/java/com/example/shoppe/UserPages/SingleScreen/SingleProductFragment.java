package com.example.shoppe.UserPages.SingleScreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.shoppe.R;

import com.example.shoppe.UserPages.UserModel.HomeUserModel;
import com.example.shoppe.UserPages.UserModel.ShopUserModel;
import com.example.shoppe.databinding.FragmentSingleProductBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SingleProductFragment extends Fragment {

    private FragmentSingleProductBinding binding;
    private HomeUserModel data;
    String nodeId = " ";
    Boolean isHeartSelected = false;
    public SingleProductFragment() {
        // Required empty public constructor
    }

    public SingleProductFragment(String nodeId, HomeUserModel data) {
        this.data = data;
        this.nodeId = nodeId;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSingleProductBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Glide.with(getContext()).load(data.getProductImage()).into(binding.productImage);
        binding.productDescription.setText(data.getProductDescription());
        binding.productPrice.setText(data.getProductPrice());





        binding.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isHeartSelected)
                {
                    binding.wishlist.setImageResource(R.drawable.heart);
                }
                else
                {
                    binding.wishlist.setImageResource(R.drawable.heart_empty);

                }
                isHeartSelected = !isHeartSelected;
            }
        });

        // this code for add item into cart
        binding.AddtoCard.setOnClickListener(v -> {

            if(nodeId != "")
            {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference reference = database.getReference().child("AddToCart/");
//                String uniqueName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() ;
//                HomeUserModel model = new HomeUserModel(data.getProductName() ,
//                        data.getProductDescription() , data.getProductPrice() ,
//                        data.getProductImage());
//
//                reference.child(uniqueName).setValue(model);
                SharedPreferences preferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                String email = preferences.getString("email", "no email id");
                String userId = preferences.getString("userId", "no userIid");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference().child("AddToCart/");

                // Assuming data.getProductName() contains the product name you want to check
                String productNameToCheck = data.getProductName();

                // Query to check if the productName already exists
                Query query = reference.child(userId).orderByChild("productName").equalTo(productNameToCheck);


                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(getContext(), "" + "item is already exists", Toast.LENGTH_SHORT).show();


                            HomeUserModel model = new HomeUserModel(data.getProductName(),
                                    data.getProductDescription(), data.getProductPrice(),
                                    data.getProductImage());

                            String existingProductId = dataSnapshot.getChildren().iterator().next().getKey();
                            int productQuantity = dataSnapshot.child(existingProductId).child("productQuantity").getValue(int.class);
                            String productPrice = dataSnapshot.child(existingProductId).child("productPrice").getValue(String.class);
                            int convertedProductPrice = Integer.parseInt(productPrice);
                            int totalPrice = productQuantity * convertedProductPrice;
                            reference.child(userId).child(existingProductId).child("productQuantity").setValue(productQuantity + 1 );
                            reference.child(userId).child(existingProductId).child("totalPrice").setValue(totalPrice );

                        } else {
                            // Product with the same name does not exist, proceed with insertion
                            String uniqueName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis();
                            ShopUserModel model = new ShopUserModel(data.getProductName(),
                                    data.getProductDescription(), data.getProductPrice(),
                                    data.getProductImage() , 1 );

                            reference.child(userId).child(uniqueName).setValue(model);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle errors
                    }
                });


            }else {
                Toast.makeText(getContext(), "node id is not found", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(getContext(), "clicked on add to cart" + nodeId , Toast.LENGTH_SHORT).show();

        });




        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release the binding when the view is destroyed
    }
}
