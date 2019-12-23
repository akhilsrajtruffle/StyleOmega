package com.example.styleomega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.styleomega.Model.Prevalent.Prevalent;
import com.example.styleomega.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private EditText inputPhoneNumber, inputPassword;
    private Button loginButton;
    private CheckBox rememberMeCheckbox;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";
    private TextView adminLink, notAdminLink, registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);


        registerLink = (TextView) findViewById(R.id.register_link);
        inputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        inputPassword = (EditText) findViewById(R.id.login_password_input);
        loginButton = (Button) findViewById(R.id.login_btn);
        loadingBar = new ProgressDialog(this);

        adminLink = (TextView) findViewById(R.id.admin_panel_link);
        notAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);

        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login Admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButton.setText("Login ");
                adminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";

            }
        });


        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        rememberMeCheckbox = (CheckBox) findViewById(R.id.remember_me_chk);

        String userPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String userPasswordKey = Paper.book().read(Prevalent.userPasswordKey);


        if (userPasswordKey != "" && userPhoneKey != "") {


            if (!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey)) {

                loadingBar.setTitle("Already logged in");
                loadingBar.setMessage("Please wait ");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();


                allowAccess(userPhoneKey, userPasswordKey);   //remember me function

            }

        }


    }

    private void allowAccess(final String phone, final String password) {      //remember me function

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ((dataSnapshot.child("Users").child(phone).exists())) {

                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone)) {

                        if (usersData.getPassword().equals(password)) {

                            Toast.makeText(MainActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Account with this " + phone + " number do not exist", Toast.LENGTH_SHORT).show();

                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loginUser() {


        String phone = inputPhoneNumber.getText().toString();
        String password = inputPassword.getText().toString();


        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            allowAccessToAccount(phone, password);
        }
    }

    private void allowAccessToAccount(final String phone, final String password) {

        if (rememberMeCheckbox.isChecked()) {
            Paper.book().write(Prevalent.userPhoneKey, phone);
            Paper.book().write(Prevalent.userPasswordKey, password);
        }

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ((dataSnapshot.child(parentDbName).child(phone).exists())) {

                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone)) {

                        if (usersData.getPassword().equals(password)) {

                            if (parentDbName.equals("Admins")) {

                                Toast.makeText(MainActivity.this, "Admin Successful Login", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);

                            } else if (parentDbName.equals("Users")) {

                                Toast.makeText(MainActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);

                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Account with this " + phone + " number do not exist", Toast.LENGTH_SHORT).show();

                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}