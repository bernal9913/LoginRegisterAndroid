package com.bernalgas.finalchaval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class postLogin extends AppCompatActivity {
    //LoginResponse loginResponse;
    TextView u, e, n, b;
    FloatingActionButton noti,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        // getting extras from the another activity
        Intent intent = getIntent();
        String usr = intent.getStringExtra("usr");
        System.out.println("postlogin");
        //System.out.println(usr);

        // parsing json string to user object
        User user = new Gson().fromJson(usr,User.class);
        System.out.println(user.getEmail());

        u = findViewById(R.id.tv_pUsername);
        e = findViewById(R.id.tv_pEmail);
        n = findViewById(R.id.tv_pNationality);
        b = findViewById(R.id.tv_pBirthdate);

        // floating action button
        noti = findViewById(R.id.fab_notification);
        logout = findViewById(R.id.fab_logout);

        u.setText(user.getUsername());
        e.setText(user.getEmail());
        n.setText(user.getNationality());
        b.setText(user.getBirthdate());

        // TODO eventually make it work the logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Adios " + user.getUsername() + ", fue un gusto verte";
                Toast.makeText(postLogin.this, msg, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // TODO also eventually make it work the notification button
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Eventualmente funcionara";
                Toast.makeText(postLogin.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}