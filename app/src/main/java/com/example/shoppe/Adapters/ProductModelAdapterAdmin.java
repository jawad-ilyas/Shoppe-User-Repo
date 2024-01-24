package com.example.shoppe.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppe.GlobalFunctions;

import com.example.shoppe.Interface.ItemClickedListener;
import com.example.shoppe.Models.BrandModelAdmin;
import com.example.shoppe.Models.CategoryModelAdmin;
import com.example.shoppe.Models.ProductModelAdmin;
import com.example.shoppe.R;
import com.example.shoppe.databinding.DialogBoxProductLayoutBinding;
import com.example.shoppe.databinding.SingleProductDesignBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.HashMap;

    public class ProductModelAdapterAdmin extends FirebaseRecyclerAdapter<ProductModelAdmin,
            ProductModelAdapterAdmin.MyProductViewHodler> implements ItemClickedListener {

        @Override
        public void productItemSelected(ProductModelAdmin data){}

        ItemClickedListener clickedListener;


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






        Context context = holder.binding.productDescription.getContext();
        clickedListener = (ItemClickedListener) context;

        holder.itemView.setOnClickListener(v -> {
            if (clickedListener != null) {
                Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show();
                clickedListener.productItemSelected(new ProductModelAdmin(model.getProductName(),
                        model.getProductDescription(),
                        model.getProductPrice(),model.getProductImage() ));
            }
        });

        holder.binding.update.setOnClickListener(v -> {


            GlobalFunctions globalFunctions = new GlobalFunctions();
            DialogBoxProductLayoutBinding productLayoutBinding;
            productLayoutBinding = DialogBoxProductLayoutBinding.inflate(LayoutInflater.from(context));

            AlertDialog.Builder updateDialog = new AlertDialog.Builder(context);

            updateDialog.setTitle("Update Product");
            updateDialog.setIcon(R.drawable.edit_3_svgrepo_com);
            updateDialog.setView(productLayoutBinding.getRoot());
            productLayoutBinding.dialogName.setText(model.getProductName());
            productLayoutBinding.dialogDescription.setText(model.getProductDescription());
            productLayoutBinding.dialogPrice.setText(model.getProductPrice());
            Glide.with(context)
                    .load(model.getProductImage())  // Assuming that model.getImagePath() returns the URL or URI of the image
                    .placeholder(R.drawable.admin_img)  // You can use a placeholder drawable while the image is loading
                    .error(R.drawable.upload_image_avatar)  // You can use an error drawable if Glide fails to load the image
                    .into(productLayoutBinding.dialogImage);
                    updateDialog.setPositiveButton("Update", (((dialog, which) -> {




                String productName = globalFunctions.FieldText(productLayoutBinding.dialogName);
                String productPrice = globalFunctions.FieldText(productLayoutBinding.dialogDescription);
                String productDescription = globalFunctions.FieldText(productLayoutBinding.dialogPrice);



                HashMap<String , Object> hashMap = new HashMap<>();
                hashMap.put("productName" , productName);
                hashMap.put("productPrice" , productPrice);
                hashMap.put("productDescription" ,productDescription );
                getRef(position).updateChildren(hashMap).addOnSuccessListener(unused -> {

                    Toast.makeText(context, "Product is Updated " , Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Product is Not Updated " , Toast.LENGTH_SHORT).show();

                });

            }))).setNegativeButton("Cancel" , (((dialog, which) -> {

            })));
            updateDialog.show();





            Toast.makeText(context, "clicked on update", Toast.LENGTH_SHORT).show();
        });


        holder.binding.delete.setOnClickListener(v -> {

            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);

            deleteDialog.setIcon(R.drawable.delete_svgrepo_com);
            deleteDialog.setMessage("Are Your Sure You Want To delete");
            deleteDialog.setTitle("Delete Product");

            deleteDialog.setPositiveButton("Delete" , (dialog, which) -> {
               getRef(position).removeValue();
            }).setNegativeButton("Cancel" , (dialog, which) -> {

            });
            deleteDialog.show();
        });
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
