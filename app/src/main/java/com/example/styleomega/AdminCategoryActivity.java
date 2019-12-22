package com.example.styleomega;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AdminCategoryActivity extends AppCompatActivity {

    private LinearLayout tShirts,denimsJeans,sweaters,dresses,swimwear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        tShirts = (LinearLayout) findViewById(R.id.tShirt);
        denimsJeans = (LinearLayout) findViewById(R.id.denims_jeans);
        sweaters = (LinearLayout) findViewById(R.id.Sweaters);
        dresses = (LinearLayout) findViewById(R.id.dresses);
        swimwear = (LinearLayout) findViewById(R.id.swimwear);




        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProduct.class);
                intent.putExtra("category","tShirts");
                startActivity(intent);

            }
        });
        denimsJeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProduct.class);
                intent.putExtra("category","denimsJeans");
                startActivity(intent);

            }
        });
        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProduct.class);
                intent.putExtra("category","sweaters");
                startActivity(intent);

            }
        });
        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProduct.class);
                intent.putExtra("category","dresses");
                startActivity(intent);

            }
        });
        swimwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProduct.class);
                intent.putExtra("category","swimwear");
                startActivity(intent);

            }
        });


    }
}
