package com.bernalgas.finalchaval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MousekeTools extends AppCompatActivity {
    EditText em, pa;
    Button b, demote;

    String ENDPOINT = "https://192.168.31.236:5000/users/mouseketools";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouseke_tools);
        startEverything();

        demote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DemoteUser.class);
                startActivity(i);

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = em.getText().toString().trim();
                System.out.println(e);
                String p = pa.getText().toString().trim();
                System.out.println(p);
                if(TextUtils.isEmpty(e) || TextUtils.isEmpty(p)){
                    Toast.makeText( MousekeTools.this,
                            "Llena los campos, o no eres digno para dar poderes a alguien",
                            Toast.LENGTH_SHORT).show();
                }else{
                    String[] credenciales = {e, p, ENDPOINT};
                    System.out.println(e);
                    System.out.println(p);
                    System.out.println(ENDPOINT);
                    System.out.println(Arrays.toString(credenciales));
                    API api = new API();
                    api.execute(credenciales);
                }
            }
        });
    }
    private void startEverything(){
        em = findViewById(R.id.tf_chooseEmail);
        pa = findViewById(R.id.tf_mousePassword);
        b = findViewById(R.id.btn_mousePromote);
        demote = findViewById(R.id.btn_changePassword);
    }


    private class API extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... credenciales)
        {
            String respuesta = "";
            String email = credenciales[0];
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
                String payload = "{\n   \"email\" : \""+email+"\",\n   \"password\" : \""+password+"\"\n}";
                //String vv = "{\n \"user\":\""+u+",\n \"password\":\""+p+",\"\n \"email\":\n \""+e+",\"\n                    \n}";
                JSONObject json = new JSONObject();
                json.put("email", email);
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

                if(respMsg.equals("Admin password is incorrect")){
                    Toast.makeText(MousekeTools.this, "Clave de administrador invalida rey", Toast.LENGTH_LONG).show();
                }else if(respMsg.equals("Error making upgrading user")){
                    Toast.makeText(MousekeTools.this, "Un error de mayor escala nos dio pedillos", Toast.LENGTH_LONG).show();
                }
                else if(respMsg.equals("Successfully upgraded user")){

                    Toast.makeText(MousekeTools.this, "Bienvenido nuevo mortal al mundo admin",Toast.LENGTH_SHORT).show();
                    Toast.makeText(MousekeTools.this, "Vuelve a logearte para tener privilegios de admin", Toast.LENGTH_SHORT).show();
                    finish();
                }else if(respMsg.equals("User not found")){
                    Toast.makeText(MousekeTools.this, "No hay a quien darle poder rey", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}