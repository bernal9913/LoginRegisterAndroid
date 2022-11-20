package com.bernalgas.finalchaval;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

//import com.github.drjacky.imagepicker.ImagePicker;
//import com.github.drjacky.imagepicker.constant.ImageProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class ChangeDP extends AppCompatActivity {
    ImageView profileImageView, gProfileImage;
    Button pickImage;
    SharedPreferences sharedPreferences;
    private static final int SELECT_IMAGE = 100;
    private static final int REQUEST_IMAGE_GET = 1;
    private boolean hasImage = false;
    dbStopJumper db;
    Bitmap imageDB;
    String USER;

    DatabaseReference databaseReference;
    FirebaseAuth auth;
    StorageTask uploadTask;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_dp);
        sharedPreferences = this.getSharedPreferences("sessions", Context.MODE_PRIVATE);
        profileImageView = findViewById(R.id.iv_profile);

        System.out.println("changedp");
        USER = sharedPreferences.getString("username","");
        System.out.println(sharedPreferences.getString("username",""));
        db = new dbStopJumper(this);
        Boolean ei = db.checkPhoto(USER);
        System.out.println("check photo: " + ei);
        if(ei){
            Bitmap tmp = db.getPhoto(USER);
            profileImageView.setImageBitmap(tmp);
        }
        pickImage = findViewById(R.id.btn_pick);

        if(ContextCompat.checkSelfPermission(ChangeDP.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            profileImageView.setEnabled(false);
            //ActivityCompat.requestPermissions(ChangeDP.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
        }

        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("boton miado");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET);
                }else{
                    startActivityForResult(intent, REQUEST_IMAGE_GET);
                }
                //startActivityForResult(Intent.createChooser(intent,"Seleccionar imagen"),SELECT_IMAGE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_IMAGE && null != data){
            String user = sharedPreferences.getString("username","");
            Uri uri = data.getData();
            /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
            byte[] img = byteArray.toByteArray();
            boolean insr = dbStopJumper.insertData(user, img);
            if(insr){
                Toast.makeText(ChangeDP.this, "Foto cambiada", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ChangeDP.this, "Algo anda mal", Toast.LENGTH_SHORT).show();
            }
            profileImageView.setImageBitmap(dbStopJumper.getIMG(user));

             */
            profileImageView.setImageURI(uri);
        }
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            //Bitmap thumbnail = data.getParcelable("data");
            if (data != null) {
                Uri fullPhotoUri = data.getData();
                //profileImageView.setImageURI(fullPhotoUri);
                Bitmap bitmap = null;
                try {
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(),fullPhotoUri));
                    profileImageView.setImageBitmap(bitmap);
                    System.out.println("bitmap");
                    Bitmap bmp = bitmap;

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();

                    //boolean checku = dbHelper.checkPhoto(USER);

                    //String encodedImage = Base64.getEncoder().encodeToString(b);
                    //System.out.println(encodedImage);
                    System.out.println(USER);
                    db.addPhoto(USER, bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this,getContentResolver(), fullPhotoUri);
            }
        }
    }

}