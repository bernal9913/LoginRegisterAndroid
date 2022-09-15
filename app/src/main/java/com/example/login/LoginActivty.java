package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivty extends AppCompatActivity {

    EditText user, pass;
    Button login;
    TextView register;
    dbStopJumper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        user = findViewById(R.id.tf_regName);
        pass = findViewById(R.id.tf_registerPass);
        login = findViewById(R.id.btn_register);
        register = findViewById(R.id.tv_dontHaveAcc);
        db = new dbStopJumper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = user.getText().toString();
                String p = pass.getText().toString();
                if(TextUtils.isEmpty(u) || TextUtils.isEmpty(p)){
                    Toast.makeText( LoginActivty.this,
                            "Todos los campos de texto deben de tener información",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkUserPass = db.checkUserPass(u,p);
                    if(checkUserPass){
                        Toast.makeText(LoginActivty.this,
                                "Loggeo exitoso",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(LoginActivty.this,
                                "Usuario o contraseña no coinciden",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}