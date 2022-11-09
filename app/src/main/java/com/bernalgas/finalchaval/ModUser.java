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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ModUser extends AppCompatActivity {
    EditText pass, em;
    Button bt;
    String ENDPOINT = "https://kfreeze-api.herokuapp.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_user);
        startEverything();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = em.getText().toString().trim();
                System.out.println(e);
                String p = pass.getText().toString().trim();
                System.out.println(p);
                if(TextUtils.isEmpty(e) || TextUtils.isEmpty(p)){
                    Toast.makeText(ModUser.this,"Llena todos los campos para continuar", Toast.LENGTH_SHORT).show();
                }else{
                    String[] credenciales = {e,p, ENDPOINT};
                    System.out.println(e);

                    System.out.println(ENDPOINT);
                    System.out.println(Arrays.toString(credenciales));
                    API api = new API();
                    api.execute(credenciales);
                }
            }
        });
    }
    private void startEverything(){
        pass = findViewById(R.id.tf_choosePass);
        em = findViewById(R.id.tf_chooseEmail);
        bt = findViewById(R.id.btn_changePassword);

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
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "*/*");
                conn.setDoOutput(true);
                String payload = "{\n   \"email\" : \""+email+"\",\n   \"password\" : \""+password+"\"\n}";
                //String vv = "{\n \"user\":\""+u+",\n \"password\":\""+p+",\"\n \"email\":\n \""+e+",\"\n                    \n}";
                JSONObject json = new JSONObject();
                json.put("email", email);
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
                System.out.println(respMsg);

                if(respMsg.equals("Error modifying user")){
                    Toast.makeText(ModUser.this, "Error externo:C", Toast.LENGTH_LONG).show();
                }else if(respMsg.equals("Successfully modified user")){
                    Toast.makeText(ModUser.this, "Usuario modificado con exito ",Toast.LENGTH_SHORT).show();
                    finish();
                }else if(respMsg.equals("user not found")){
                    Toast.makeText(ModUser.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}