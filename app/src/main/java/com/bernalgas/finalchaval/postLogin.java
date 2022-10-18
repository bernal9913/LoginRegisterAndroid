package com.bernalgas.finalchaval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class postLogin extends AppCompatActivity {
    //LoginResponse loginResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        Intent intent = getIntent();
        // https://www.youtube.com/watch?v=ApqOpiWfc5U
        if(intent.getExtras() != null){
            //loginResponse = (LoginResponse) intent.getSerializableExtra("data");
            //Log.e("TAG", "=====> " + loginResponse.getEmail());
        }
    }
}