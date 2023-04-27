package com.bernalgas.finalchaval;

import androidx.appcompat.app.AppCompatActivity;

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

public class DeleteUser extends AppCompatActivity {
    EditText em1, em2;
    Button b;
    String ENDPOINT = "https://192.168.31.236:5000/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        startEverything();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("onclick");
                String e1 = em1.getText().toString().trim();
                System.out.println(e1);
                String e2 = em2.getText().toString().trim();
                System.out.println(e2);
                if (TextUtils.isEmpty(e1) || TextUtils.isEmpty(e2)){
                    Toast.makeText(DeleteUser.this, "Llena el nombre del mortal", Toast.LENGTH_SHORT).show();
                } else if (e1.equals(e2)){
                    String[] credenciales = {e1, ENDPOINT};
                    System.out.println(e1);
                    System.out.println(ENDPOINT);
                    System.out.println(Arrays.toString(credenciales));
                    API api = new API();
                    api.execute(credenciales);
                }else{
                    Toast.makeText(DeleteUser.this, "No coinciden los Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void startEverything(){
        em1 = findViewById(R.id.tf_chooseEmail);
        em2 = findViewById(R.id.tf_deleteEmail2);
        b = findViewById(R.id.btn_changePassword);
    }

    private class API extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... credenciales)
        {
            String respuesta = "";
            String email = credenciales[0];
            String endpoint = credenciales[1];
            try
            {
                URL url = new URL(endpoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "*/*");
                conn.setDoOutput(true);
                String payload = "{\n   \"email\" : \""+email+"\"}";
                JSONObject json = new JSONObject();

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
                // TODO Corregir eventualmente el espacio en blanco ahi
                if(respMsg.equals("User not founded")){
                    Toast.makeText(DeleteUser.this, "El usuario no existe", Toast.LENGTH_LONG).show();
                }else if(respMsg.equals("Error: cannot drop the whole table:C")){
                    Toast.makeText(DeleteUser.this, "Ocurrio un error mayor",Toast.LENGTH_SHORT).show();
                    //finish();
                }
                else if(respMsg.equals("User deleted successfully")){
                    Toast.makeText(DeleteUser.this, "RIP: " + em1.getText().toString(),Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}