package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.styleomega.Model.Products;
import com.example.styleomega.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CategoryActivity extends AppCompatActivity {
    private LinearLayout tShirtsCat,denimsJeansCat,sweatersCat,dressesCat,swimwearCat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        tShirtsCat = (LinearLayout) findViewById(R.id.tShirt_cat);
        denimsJeansCat = (LinearLayout) findViewById(R.id.denims_jeans_cat);
        sweatersCat = (LinearLayout) findViewById(R.id.Sweaters_cat);
        dressesCat = (LinearLayout) findViewById(R.id.dresses_cat);
        swimwearCat = (LinearLayout) findViewById(R.id.swimwear_cat);




        tShirtsCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,ViewCategoryActivity.class);
                intent.putExtra("category","tShirts");
                startActivity(intent);

            }
        });
        denimsJeansCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,ViewCategoryActivity.class);
                intent.putExtra("category","denimsJeans");
                startActivity(intent);

            }
        });
        sweatersCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,ViewCategoryActivity.class);
                intent.putExtra("category","sweaters");
                startActivity(intent);

            }
        });
        dressesCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,ViewCategoryActivity.class);
                intent.putExtra("category","dresses");
                startActivity(intent);

            }
        });
        swimwearCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,ViewCategoryActivity.class);
                intent.putExtra("category","swimwear");
                startActivity(intent);

            }
        });
    }

   // private void getData(String category) {
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
//
//        FirebaseRecyclerOptions<Products> options =
//                new FirebaseRecyclerOptions.Builder<Products>().setQuery(reference.orderByChild("category").startAt(category),Products.class).build();
//
//        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
//
//                        holder.txtProductName.setText(model.getProductName());
//                        // productViewHolder.txtProductDescription.setText(products.getDescription());
//                        holder.txtProductPrice.setText(model.getPrice() + "$");
//                        Picasso.get().load(model.getImage()).fit().centerCrop().into(holder.imageView);
//
//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                                Intent intent = new Intent(CategoryActivity.this,ProductDetailsActivity.class);
//                                intent.putExtra("pid",model.getPid());
//                                startActivity(intent);
//                            }
//                        });
//                    }
//
//                    @NonNull
//                    @Override
//                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
//                        ProductViewHolder holder = new ProductViewHolder(view);
//                        return holder;
//                    }
//                };
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//
//
//
//    }


}
