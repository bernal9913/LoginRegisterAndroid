package com.bernalgas.finalchaval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText user, pass;
    Button login;
    TextView register;
    //dbStopJumper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.tf_regName);
        pass = findViewById(R.id.tf_registerPass);
        login = findViewById(R.id.btn_register);
        register = findViewById(R.id.tv_dontHaveAcc);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = user.getText().toString();
                String p = pass.getText().toString();
                //String authToken = createAuthToken(u,p);
                if(TextUtils.isEmpty(u) || TextUtils.isEmpty(p)){
                    Toast.makeText( Login.this,
                            "Todos los campos de texto deben de tener información",
                            Toast.LENGTH_SHORT).show();
                }else{
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setUsername(u);
                    loginRequest.setPassword(p);

                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });
    }

    public void loginUser(LoginRequest loginRequest){
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    startActivity(new Intent(Login.this, postLogin.class).putExtra("data", String.valueOf(loginResponse)));
                    finish();
                }else{
                    String msg = "An error occurred please try again leiter";
                    Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String msg = t.getLocalizedMessage();
                Toast.makeText(Login.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}