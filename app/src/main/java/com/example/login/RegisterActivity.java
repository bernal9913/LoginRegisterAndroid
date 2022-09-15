package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {
    EditText user, pass, repass, email, nation, date;
    Button register;
    // DatePicker date;
    dbStopJumper db;
    Spinner nation1;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.tf_regName);
        pass = findViewById(R.id.tf_registerPass);
        repass = findViewById(R.id.tf_registerRepass);
        email = findViewById(R.id.tf_registerMail);
        nation1 = findViewById(R.id.tf_registerOrigin1);
        date = findViewById(R.id.dp_birthDate);
        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.tv_log);
        db = new dbStopJumper(this);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String u = user.getText().toString();
                String p = pass.getText().toString();
                String rp = repass.getText().toString();
                String e = email.getText().toString();
                String n = nation1.getSelectedItem().toString();
                String d = date.getText().toString();
                if(TextUtils.isEmpty(u) || TextUtils.isEmpty(p) || TextUtils.isEmpty(e) || TextUtils.isEmpty(n) || TextUtils.isEmpty(d) || TextUtils.isEmpty(rp)){
                    Toast.makeText(RegisterActivity.this,
                            "Todos los campos deben estar llenos",
                            Toast.LENGTH_SHORT).show();
                }else{
                    if(p.equals(rp)){
                        Boolean checkUser = db.checkUser(u);
                        if (!checkUser){
                            Boolean insert = db.insertData(u,p,e,d,n);
                            if(insert){
                                Toast.makeText(RegisterActivity.this,
                                "Registro exitoso", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), LoginActivty.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Registro fallido️", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "El usuario ya existe ☹️", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
        );
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivty.class);
                startActivity(i);
            }
        });

    }
}