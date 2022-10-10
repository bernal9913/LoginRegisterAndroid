package com.bernalgas.finalchaval;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    EditText user, pass, repass, email, nation, date;
    Button register;
    // DatePicker date;
    // dbStopJumper db;
    Spinner nation1;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //https://www.youtube.com/watch?v=7aRn2Ch7Cs0
        user = findViewById(R.id.tf_regName);
        pass = findViewById(R.id.tf_registerPass);
        repass = findViewById(R.id.tf_registerRepass);
        email = findViewById(R.id.tf_registerMail);
        nation1 = findViewById(R.id.tf_registerOrigin1);
        date = findViewById(R.id.dp_birthDate);
        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.tv_alreadyHaveAcc);
        //db = new dbStopJumper(this);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String u = user.getText().toString();
                String p = pass.getText().toString();
                String rp = repass.getText().toString();
                String e = email.getText().toString();
                String n = nation1.getSelectedItem().toString();
                String d = date.getText().toString();
                if(TextUtils.isEmpty(u) || TextUtils.isEmpty(p) ||
                        TextUtils.isEmpty(e) || TextUtils.isEmpty(n) ||
                        TextUtils.isEmpty(d) || TextUtils.isEmpty(rp)){
                    Toast.makeText(Register.this,
                            "Todos los campos deben estar llenos",
                            Toast.LENGTH_SHORT).show();
                }else{
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setUsername(u);
                    registerRequest.setPassword(p);
                    registerRequest.setEmail(e);
                    registerRequest.setPlaceOfBirth(n);
                    registerRequest.setBirthDate(d);
                    registerUser(registerRequest);
                }
            }
        }
        );
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });
    }

    public void registerUser(RegisterRequest registerRequest){
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                    String msg = "Exito pa . . .";
                    Toast.makeText(Register.this, msg, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, Login.class));
                    finish();
                }else{
                    String msg = "An error occurred please try again leiter";
                    Toast.makeText(Register.this, msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String msg = t.getLocalizedMessage();
                Toast.makeText(Register.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}