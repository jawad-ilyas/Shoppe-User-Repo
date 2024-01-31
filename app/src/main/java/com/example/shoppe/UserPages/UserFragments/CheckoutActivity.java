package com.example.shoppe.UserPages.UserFragments;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.shoppe.CreateAccount;
import com.example.shoppe.GlobalFunctions;
import com.example.shoppe.MainActivity;
import com.example.shoppe.R;
import com.example.shoppe.UserPages.Adapters.CheckoutUserAdapter;
import com.example.shoppe.UserPages.Adapters.ShopUserAdapter;
import com.example.shoppe.UserPages.UserIntro;
import com.example.shoppe.UserPages.UserModel.CheckoutUserModel;
import com.example.shoppe.UserPages.UserModel.ShopUserModel;
import com.example.shoppe.databinding.ActivityCheckoutBinding;
import com.example.shoppe.databinding.SingleAddressPopupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CheckoutActivity extends AppCompatActivity {
    CheckoutUserAdapter adapter;
    String paymentMethod ;
    GlobalFunctions globalFunctions;
     String addressAddress , addressCity , addressZipCode ;
     String zipCodeValue , cityValue , addressValue ;
    String AddToCart = "AddToCart/";
    ActivityCheckoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                startActivity(new Intent(CheckoutActivity.this , UserIntro.class));
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this , callback);


        SharedPreferences preferences = getSharedPreferences( "loginInfo" , MODE_PRIVATE);
        String email = preferences.getString("email", "no email id");
        String userId = preferences.getString("userId", "no userIid");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("userAddressOffer/").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // User data found, you can access it using snapshot.getValue()
                    String addressAddress = snapshot.child("addressAddress").getValue(String.class);
                    String addressCity = snapshot.child("addressCity").getValue(String.class);
                    binding.shippingAddress.setText(addressAddress + addressCity);

                    // Do something with the data
                    // For example, update UI elements
                } else {
                    // User data not found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });

        if(email != "")
        {
            binding.userEmail.setText(email);
            globalFunctions = new GlobalFunctions();
        }



        binding.AddressPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SingleAddressPopupBinding singleAddressBinding = SingleAddressPopupBinding.inflate(getLayoutInflater());
                AlertDialog.Builder addressBuilder = new AlertDialog.Builder(CheckoutActivity.this);

                addressBuilder.setTitle("Fill Shipping Address");
                addressBuilder.setView(singleAddressBinding.getRoot());

                EditText addressAddress = singleAddressBinding.addressAddress;
                EditText addressCity = singleAddressBinding.addressCity;
                EditText addressZipCode = singleAddressBinding.addressZipCode;

                singleAddressBinding.adddressAddBtn.setOnClickListener(v1 -> {
                    String addressValue = addressAddress.getText().toString().trim();
                    String cityValue = addressCity.getText().toString().trim();
                    String zipCodeValue = addressZipCode.getText().toString().trim();

                    if (!globalFunctions.validateField(addressValue, addressAddress, "Address is required")) {
                        return;
                    } else if (!globalFunctions.validateField(cityValue, addressCity, "City is required")) {
                        return;
                    } else if (!globalFunctions.validateField(zipCodeValue, addressZipCode, "Zip code is required")) {
                        return;
                    } else {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = database.getReference("userAddressOffer/");

                        Query query = databaseReference.child(userId);


                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists())
                                {

                                    HashMap<String  , Object> addressMap = new HashMap<>();
                                    addressMap.put("addressAddress",addressValue );
                                    addressMap.put("addressCity",cityValue );
                                    addressMap.put("addressZipCode",zipCodeValue );

                                    databaseReference.child(userId).updateChildren(addressMap);
                                    binding.shippingAddress.setText(addressValue + cityValue);

                                }
                                else {
                                    binding.shippingAddress.setText(addressValue + cityValue);

                                    HashMap<String  , Object> addressMap = new HashMap<>();
                                    addressMap.put("addressAddress",addressValue );
                                    addressMap.put("addressCity",cityValue );
                                    addressMap.put("addressZipCode",zipCodeValue );

                                    databaseReference.child(userId).setValue(addressMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(CheckoutActivity.this, "Shipping address is added", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CheckoutActivity.this, "Shipping address is failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

               addressBuilder.show();

            }
        });
        binding.payBtn.setOnClickListener(v -> {
                
            if(paymentMethod.isEmpty())
            {
                Toast.makeText(this, "Please Select Payment Method",
                        Toast.LENGTH_SHORT).show();
            }
            else {

                String uniqueName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() ;

                FirebaseDatabase checkoutDatabase = FirebaseDatabase.getInstance();
                DatabaseReference cartReferenceDb = checkoutDatabase.getReference("AddToCart/" + userId);
                DatabaseReference checkoutReferenceDb = checkoutDatabase.getReference("CheckoutDetails/" + userId +"/" +uniqueName );
                cartReferenceDb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Map<String, Object> cartData = (Map<String, Object>) dataSnapshot.getValue();
                            checkoutReferenceDb.setValue(cartData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {


                                            cartReferenceDb.removeValue();
                                            startActivity(new Intent(CheckoutActivity.this , UserIntro.class));
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                        } else {
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error, if needed
                    }
                });


            }
            
            

        });

        // working with checkboxing

        binding.cashMethod.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, "cashMethod is clicked", Toast.LENGTH_SHORT).show();
            paymentMethod = "cash";
        });
        binding.cardMethod.setEnabled(false);
        binding.cardMethod.setOnCheckedChangeListener((buttonView, isChecked) -> {
            paymentMethod = "card";
            Toast.makeText(this, "cardMethod is clicked", Toast.LENGTH_SHORT).show();
        });







        adapter = globalFunctions.fetchDataFromFireBase(binding.checkoutRecyclerView ,
                AddToCart+userId , this, CheckoutUserModel.class , CheckoutUserAdapter.class , true);

    }
    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }

}