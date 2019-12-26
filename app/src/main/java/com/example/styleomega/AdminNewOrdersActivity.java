package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.styleomega.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrdersActivity extends AppCompatActivity {

    private RecyclerView orderList;
    private DatabaseReference ordersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);


        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        orderList = findViewById(R.id.orders_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));




    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef,AdminOrders.class)
                        .build();


        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder> adapter = new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, int position, @NonNull AdminOrders model) {

                holder.username.setText("Name: "+ model.getName());
                holder.userphoneNumber.setText("Phone: "+ model.getPhone());
                holder.userTotalPrice.setText("Total Amount: $"+ model.getTotalAmount());
                holder.userDateTime.setText("Order at: "+ model.getDate()+" "+model.getTime());
                holder.userShippingAddress.setText("Name: "+ model.getAddress()+ ", "+model.getCity());


            }

            @NonNull
            @Override
            public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                return new AdminOrdersViewHolder(view);
            }
        };


        orderList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView username,userphoneNumber,userTotalPrice,userDateTime,userShippingAddress;
        public Button showOrdersBtn;

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.order_user_name);
            userphoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userShippingAddress = itemView.findViewById(R.id.order_address_city);
            showOrdersBtn= itemView.findViewById(R.id.show_all_products_btn);


        }
    }
}
