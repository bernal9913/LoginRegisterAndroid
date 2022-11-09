package com.bernalgas.finalchaval;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;
/*
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;

//import com.google.volley.*;

public class Register extends AppCompatActivity {
    EditText user, pass, repass, email, nation, date;
    Button register;
    // DatePicker date;
    // dbStopJumper db;
    Spinner nation1;
    TextView login;
    DatePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //https://www.youtube.com/watch?v=7aRn2Ch7Cs0
        user = findViewById(R.id.tf_chooseEmail);
        pass = findViewById(R.id.tf_mousePassword);
        repass = findViewById(R.id.tf_registerRepass);
        email = findViewById(R.id.tf_registerMail);
        nation1 = findViewById(R.id.tf_registerOrigin1);
        date = findViewById(R.id.dp_birthDate);
        register = findViewById(R.id.btn_mouseBtn);
        login = findViewById(R.id.tv_alreadyHaveAcc);
        //db = new dbStopJumper(this);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String str = dayOfMonth + "/" + (month+1) + "/" + year;
                        String str1 = year + "-" + (month+1)+ "-"+ dayOfMonth;
                        date.setText(str1);
                    }
                }, year, month, day);
                picker.show();
                /*btnGet = findViewById(R.id.button1);
                btnGet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = "Selected date = " + eText.getText();
                        tvw.setText(str);
                    }
                });*/
            }
        });
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String u = user.getText().toString().trim();
                String p = pass.getText().toString().trim();
                String rp = repass.getText().toString().trim();
                String e = email.getText().toString().trim();
                String n = nation1.getSelectedItem().toString().trim();
                String d = date.getText().toString().trim();
                if(TextUtils.isEmpty(u) || TextUtils.isEmpty(p) ||
                        TextUtils.isEmpty(e) || TextUtils.isEmpty(n) ||
                        TextUtils.isEmpty(d) || TextUtils.isEmpty(rp)){
                    Toast.makeText(Register.this,
                            "Todos los campos deben estar llenos",
                            Toast.LENGTH_SHORT).show();
                }else{
                    String endpoint = "https://kfreeze-api.herokuapp.com/users";
                    String[] credentials = {u, p, e, n, d, endpoint};
                    System.out.println(u);
                    System.out.println(p);
                    System.out.println(e);
                    System.out.println(n);
                    System.out.println(d);
                    System.out.println(Arrays.toString(credentials));
                    //String test = "{\n 'user': '" + u + "',\n 'email':'"+e+"',\n'password': '" + p + "',\n 'birthdate':'" + d + "',\n'nationality': '" + n + "'\n}";
                    //System.out.println(test);

                    API api = new API();
                    api.execute(credentials);
                    /*RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setUsername(u);
                    registerRequest.setPassword(p);
                    registerRequest.setEmail(e);
                    registerRequest.setPlaceOfBirth(n);
                    registerRequest.setBirthDate(d);
                    User(registerRequest);
                     */
                    /*Retrofit retrofit = new Retrofit.Builder().baseUrl("https://kfreeze-api.herokuapp.com")
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    OurRetrofit ourRetrofit = retrofit.create(OurRetrofit.class);
                    OurDataSet ourDataSet = new OurDataSet(u,p,d,n,e);
                    Call<OurDataSet> call = ourRetrofit.PostData(ourDataSet);
                    call.enqueue(new Callback<OurDataSet>() {
                        @Override
                        public void onResponse(Call<OurDataSet> call, Response<OurDataSet> response) {
                            String msgImon = response.body().getJson().getMsg();
                            Toast.makeText(Register.this, msgImon, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<OurDataSet> call, Throwable t) {
                            Toast.makeText(Register.this, "Calaqueo rey", Toast.LENGTH_SHORT).show();
                        }
                    })*/
                    /*try {
                        URL url = new URL("https://kfreeze-api.herokuapp.com/users");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);

                        JSONObject json = new JSONObject();
                        json.put("birthdate:",d);
                        json.put("email:",e);
                        json.put("nationality:",n);
                        json.put("password:", p);
                        json.put("username;", u);
                        Log.i("JSON", json.toString());
                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        os.writeBytes(json.toString());
                        os.flush();
                        os.close();
                        //https://alecks.net/login.zip

                        Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                        Log.i("MSG", conn.getResponseMessage());
                        Toast.makeText(Register.this, "parece que al cien", Toast.LENGTH_SHORT);
                        conn.disconnect();
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    } catch (IOException | JSONException ioException ) {
                        ioException.printStackTrace();
                    }

                     */
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
/*
    public void User(RegisterRequest registerRequest){
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().User(registerRequest);
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
    }/*
    public JSONObject register(String e, String u,String p,String d,String n){
        JSONObject registerJson = null;
        try {
            JSONObject jObject = new JSONObject();
            JSONObject pObject = new JSONObject();

            jObject.put("user", u);
            jObject.put("email", e);
            jObject.put("password", p);
            jObject.put("birthdate", d);
            jObject.put("nationality", n);

            registerJson = jObject;
            Log.d("json", registerJson.toString());
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        return registerJson;
    }
    public void apiCall(JSONObject jsonObject){
        String url = "https://kfreeze-api.herokuapp.com/users";
        JsonObjectRequest
                jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                url, jsonObject, new Response,Listener
        )
    }*/
    //String payload = "\"{\\\"user\\\":\\\"" + u + "\\\",\\\"email\\\":\\\"" + e + "\\\",\\\"password\\\":\\\"" + p + "\\\",\\\"birthdate\\\":\\\""+ d +"\\\",\\\"nationality\\\":\\\"" + n + "\\\"}\"";
//
//String u = cred[0];
//        String p = cred[1];
//        String e = cred[2];
//        String n = cred[3];
//        String d = cred[4];
//        String Endpoint = cred[4];
private class API extends AsyncTask<String, String, String>
{
    @Override
    protected String doInBackground(String... cred)
    {
        String respuesta = "";
        String u = cred[0];
        String p = cred[1];
        String e = cred[2];
        String n = cred[3];
        String d = cred[4];
        String endpoint = cred[5];
        try
        {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "*/*");
            conn.setDoOutput(true);
            //String payload = "{\n   \"user\" : \""+username+"\",\n   \"pass\" : \""+password+"\"\n}";
            //String payload = "\"{\\\"user\\\":\\\"" + u + "\\\",\\\"email\\\":\\\"" + e + "\\\",\\\"password\\\":\\\"" + p + "\\\",\\\"birthdate\\\":\\\""+ d +"\\\",\\\"nationality\\\":\\\"" + n + "\\\"}\"";
            JSONObject json = new JSONObject();
            json.put("birthdate",d);
            json.put("email",e);
            json.put("nationality",n);
            json.put("password", p);
            json.put("username", u);
            String payload = json.toString();
            //String payload = "{\"user\":\"%u\",\"email\":\"+e+\",\"password\":\"+p+\",\"birthdate\":\"+d+\",\"nationality\":\"+n+\"}";
            String test ="{\n   \"user\" : \""+u+"\",\n   \"password\" : \""+p+"\",\n   \"email\" : \""+e+"\",\n   \"nationality\" : \""+n+"\",\n   \"birthdate\" : \""+d+"\"\n}";
            System.out.println(test);

            User ru = new User(u,e,p,d,n);
            Gson g = new Gson();
            String testGston = g.toJson(ru);
            System.out.println(testGston);
            //payload = testGston;
            payload = test;
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
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return respuesta;
    }


    @Override
    protected void onPostExecute(String respuesta)
    {
        try
        {
            JSONObject json = new JSONObject(respuesta);
            //resultado.setText(json.getString("status"));
            String respMsg = json.getString("msg");
            if (respMsg.equals("Successfully registered")){

                Toast.makeText(Register.this, "Por favor, inicie sesión", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);

            }
            if (respMsg.equals("Error registering user")){
                Toast.makeText(Register.this, "Algo salio mal, pero no sabemos que", Toast.LENGTH_SHORT).show();
            }

            if (respMsg.equals("User already registered")){
                Toast.makeText(Register.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
            }

            if (respMsg.equals("Email already registered")){
                Toast.makeText(Register.this, "El email ya existe", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
}