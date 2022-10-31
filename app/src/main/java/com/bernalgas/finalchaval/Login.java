package com.bernalgas.finalchaval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
    // rest in sushon belico la base de datos stopJumper
    // dbStopJumper db;
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
                    String endpoint = "https://kfreeze-api.herokuapp.com/log_user";

                    String[] credenciales = {u, p, endpoint};
                    System.out.println(u);
                    System.out.println(p);
                    System.out.println(endpoint);
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
/*
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

 */

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
                        resp.append(respLine.toString());
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
                // user data string
                String usr = json.getString("user");

                // parse string json to object user
                User user = new Gson().fromJson(usr,User.class);

                // check if all of this work its working
                System.out.println(user.getEmail());
                System.out.println(usr);
                if(respMsg.equals("user founded")){
                    Toast.makeText(Login.this, "Bienvenido",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), postLogin.class);
                    i.putExtra("usr", usr);
                    startActivity(i);

                }
                if(respMsg.equals("Error: user not founded")){
                    Toast.makeText(Login.this, "Usuario o contraseña invalidos", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}