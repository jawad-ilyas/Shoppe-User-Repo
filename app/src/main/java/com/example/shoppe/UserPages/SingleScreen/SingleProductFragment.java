package com.example.shoppe.UserPages.SingleScreen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.shoppe.R;
import com.example.shoppe.UserPages.UserHomeModel;
import com.example.shoppe.UserPages.UserModel.HomeUserModel;
import com.example.shoppe.databinding.FragmentSingleProductBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference().child("AddToCart/");
                String uniqueName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() ;
                HomeUserModel model = new HomeUserModel(data.getProductName() , data.getProductDescription() , data.getProductPrice() , data.getProductImage());
                // Use push to generate a unique key for each item
                reference.child(uniqueName).setValue(model);



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
