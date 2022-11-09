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

public class DemoteUser extends AppCompatActivity {
    EditText em;
    Button demote;
    String ENDPOINT = "https://kfreeze-api.herokuapp.com/users/demote";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demote_user);
        startEverything();
        demote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = em.getText().toString().trim();
                if (TextUtils.isEmpty(e)){
                    Toast.makeText(DemoteUser.this, "Llena el nombre del mortal", Toast.LENGTH_SHORT).show();
                }else{
                    String[] credenciales = {e, ENDPOINT};
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
        em = findViewById(R.id.tf_chooseEmail);
        demote = findViewById(R.id.btn_changePassword);
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
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "*/*");
                conn.setDoOutput(true);
                //String payload = "{\n   \"user\" : \""+username+"\",\n   \"password\" : \""+password+"\"\n}";
                //String vv = "{\n \"user\":\""+u+",\n \"password\":\""+p+",\"\n \"email\":\n \""+e+",\"\n                    \n}";
                JSONObject json = new JSONObject();
                json.put("email", email);
                String payload = json.toString();


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

                if(respMsg.equals("Error making upgrading user")){
                    Toast.makeText(DemoteUser.this, "Parece que es el elegido por dios", Toast.LENGTH_LONG).show();
                }else if(respMsg.equals("Successfully downgraded user")){

                    Toast.makeText(DemoteUser.this, "Bienvenido a la mortalidad",Toast.LENGTH_SHORT).show();
                    Toast.makeText(DemoteUser.this, "Vuelve a logearte para reestablecer tus permisos", Toast.LENGTH_SHORT).show();
                    finish();
                }else if((respMsg.equals("User not found"))){
                    Toast.makeText(DemoteUser.this, "No hay a quien bajar de rango", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}