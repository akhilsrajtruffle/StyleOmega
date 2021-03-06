package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProduct extends AppCompatActivity {

    private String categoryName, description, name, price, saveCurrentDate, saveCurrentTime;
    private Button addNewProductButton;
    private ImageView inputProductImage;
    private EditText inputProductName, inputProductDescription, inputProductPrice;
    private static final int galleryPick = 1;
    private Uri imageUri;
    private ProgressDialog loadingBar;
    private String productRandomKey, downloadImageUrl;
    private StorageReference productImagesRef;
    private DatabaseReference productRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        loadingBar = new ProgressDialog(this);

        categoryName = getIntent().getExtras().get("category").toString();
        productImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");


        addNewProductButton = (Button) findViewById(R.id.add_new_product);
        inputProductImage = (ImageView) findViewById(R.id.select_product_image);
        inputProductName = (EditText) findViewById(R.id.product_name);
        inputProductDescription = (EditText) findViewById(R.id.product_description);
        inputProductPrice = (EditText) findViewById(R.id.product_price);

        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                OpenGallery();

            }
        });

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductData();
            }
        });


    }

    private void validateProductData() {

        description = inputProductDescription.getText().toString();
        name = inputProductName.getText().toString();
        price = inputProductPrice.getText().toString();

        if (imageUri == null) {
            FancyToast.makeText(this,"Product Image is Required",FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();
//            Toast.makeText(this, "Product Image is Required", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(name)) {
            FancyToast.makeText(this, "Product Name is Required",FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();

//            Toast.makeText(this, "Product Name is Required", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(description)) {
            FancyToast.makeText(this,"Product Description is Required",FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();

//            Toast.makeText(this, "Product Description is Required", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(price)) {
            FancyToast.makeText(this,"Product Price is Required",FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();

//            Toast.makeText(this, "Product Price is Required", Toast.LENGTH_SHORT).show();
        } else {
            storeProductInformation();
        }
    }

    private void storeProductInformation() {

        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Please wait while we are adding the new product");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = productImagesRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg ");

        final UploadTask uploadTask = filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                FancyToast.makeText(AdminAddNewProduct.this,"Error " + message,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

//                Toast.makeText(AdminAddNewProduct.this, "Error " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                FancyToast.makeText(AdminAddNewProduct.this,"Product Image Uploaded Successfully ",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();


//                Toast.makeText(AdminAddNewProduct.this, "Product Image Uploaded Successfully ", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();


                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();


                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {

                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminAddNewProduct.this, "Getting Product Image", Toast.LENGTH_SHORT).show();

                            saveProductInfoDatabase();
                        }

                    }
                });
            }
        });


    }

    private void saveProductInfoDatabase() {

        HashMap<String, Object> productMap = new HashMap<>();

        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", categoryName);
        productMap.put("price", price);
        productMap.put("productName", name);

        productRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()) {

                            Intent intent = new Intent(AdminAddNewProduct.this,AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            FancyToast.makeText(AdminAddNewProduct.this,"Product is added successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

//                            Toast.makeText(AdminAddNewProduct.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();

                            FancyToast.makeText(AdminAddNewProduct.this,"Error " + message,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

//                            Toast.makeText(AdminAddNewProduct.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    private void OpenGallery() {

        Intent galleryIntent = new Intent();

        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, galleryPick);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            inputProductImage.setImageURI(imageUri);


        }
    }


}
