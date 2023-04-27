package com.bernalgas.finalchaval;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
/*
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 */

public class Login extends AppCompatActivity {
    EditText user, pass;
    Button login;
    TextView register;
    CheckBox sesion;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String ENDPOINT = "https://192.168.31.236:5000/log_user";

    // rest in sushon belico la base de datos stopJumper
    // dbStopJumper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.tf_chooseEmail);
        pass = findViewById(R.id.tf_mousePassword);
        login = findViewById(R.id.btn_mouseBtn);
        register = findViewById(R.id.tv_dontHaveAcc);
        sesion = findViewById(R.id.cb_session);
        sharedPreferences = this.getSharedPreferences("sessions",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //Boolean ssnLogeada = revisarSesion();
        if(revisarSesion()){
            String[] creds = {sharedPreferences.getString("username",""),
                    sharedPreferences.getString("password",""), ENDPOINT};
            API api = new API();
            api.execute(creds);
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = user.getText().toString().trim();
                String p = pass.getText().toString().trim();
                //String authToken = createAuthToken(u,p);
                if(TextUtils.isEmpty(u) || TextUtils.isEmpty(p)){
                    Toast.makeText( Login.this,
                            "Todos los campos de texto deben de tener información",
                            Toast.LENGTH_SHORT).show();
                }else{
                    //LoginRequest loginRequest = new LoginRequest();
                    //loginRequest.setUsername(u);
                    //loginRequest.setPassword(p);
                    //String endpoint = "https://kfreeze-api.herokuapp.com/log_user";

                    String[] credenciales = {u, p, ENDPOINT};
                    System.out.println(u);
                    System.out.println(p);
                    System.out.println(ENDPOINT);
                    System.out.println(Arrays.toString(credenciales));
                    API api = new API();
                    api.execute(credenciales);
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
    private boolean revisarSesion(){
        boolean ssn = this.sharedPreferences.getBoolean("CHECKED",false);
        return ssn;
    }



    private class API extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... credenciales)
        {
            String respuesta = "";
            String username = credenciales[0];
            String password = credenciales[1];
            String endpoint = credenciales[2];
            try
            {
                URL url = new URL(endpoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "*/*");
                conn.setDoOutput(true);
                String payload = "{\n   \"user\" : \""+username+"\",\n   \"password\" : \""+password+"\"\n}";
                //String vv = "{\n \"user\":\""+u+",\n \"password\":\""+p+",\"\n \"email\":\n \""+e+",\"\n                    \n}";


                JSONObject json = new JSONObject();
                json.put("user", username);
                json.put("password", password);
                //String payload = json.toString();


                System.out.println(payload);

                try (OutputStream os = conn.getOutputStream())
                {
                    byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)))
                {
                    StringBuilder resp = new StringBuilder();
                    String respLine = null;
                    while ((respLine = br.readLine()) != null)
                    {
                        resp.append(respLine);
                    }
                    respuesta = resp.toString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return respuesta;
        }


        @Override
        protected void onPostExecute(String respuesta)
        {
            try
            {
                JSONObject json = new JSONObject(respuesta);
                //resultado.setText(json.getString("msg"));
                // response string
                String respMsg = json.getString("msg");
                System.out.println(respMsg);

                if(respMsg.equals("Error: user not founded")){
                    Toast.makeText(Login.this, "Usuario o contraseña invalidos", Toast.LENGTH_LONG).show();
                }else if(respMsg.equals("user founded")){
                    // user data string
                    String usr = json.getString("user");
                    User user = new Gson().fromJson(usr,User.class);
                    System.out.println(user.getEmail());
                    System.out.println(user.getUserType());
                    System.out.println(user.getFirstName());
                    System.out.println(user.getLastName());
                    String nf = "Contacte al admin para registrar nuevos datos";
                    System.out.println(usr);
                    Toast.makeText(Login.this, "Bienvenido: " + user.getUsername(),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), postLogin.class);

                    editor.putBoolean("CHECKED", sesion.isChecked());
                    editor.putString("username", user.getUsername());
                    editor.putString("email", user.getEmail());
                    editor.putString("password", user.getPassword());
                    editor.putString("birthdate", user.getBirthdate());
                    editor.putString("nationality", user.getNationality());
                    editor.putString("userType", user.getUserType());

                    if(user.getFirstName().equals("")){
                        editor.putString("firstName", nf);
                    }else{
                        editor.putString("firstName", user.getFirstName());
                    }

                    if(user.getLastName().equals("")){
                        editor.putString("lastName", nf);
                    }else{
                        editor.putString("lastName", user.getFirstName());
                    }
                    //editor.putString("lastName", user.getLastName());
                    editor.apply();
                    i.putExtra("usr", usr);
                    startActivity(i);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}