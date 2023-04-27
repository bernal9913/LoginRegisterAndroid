package com.bernalgas.finalchaval;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class dbStopJumper extends SQLiteOpenHelper {
    Context context;
    public static final String DBNAME = "otradb.db";
    public static final String TABLE_USER = "tableimage";
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;
    public dbStopJumper(Context context) {
        super(context, DBNAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tableimage(name text PRIMARY KEY, image blob);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tableimage");
    }

    public boolean addPhoto(String user, Bitmap bm){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean ei = false;
        //Bitmap imageToStoreBitmap = bm;
        byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        imageInBytes = byteArrayOutputStream.toByteArray();
        System.out.println("img");

        ContentValues values = new ContentValues();
        values.put("name", user);
        values.put("image", imageInBytes);
        System.out.println("add put");
        long check = db.insert("tableimage",null, values);
        return check != -1;
    }
    public boolean updatePhoto(String user, Bitmap bm){
        boolean ei = false;
        SQLiteDatabase db = this.getWritableDatabase();
        byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        imageInBytes = byteArrayOutputStream.toByteArray();
        ContentValues values = new ContentValues();
        values.put("image", imageInBytes);
        String []args = new String[]{user};
        long check = db.update("tableimage",values,"name=?",args);
        return check != -1;
    }
    // para ver si el mecoboy existe
    public Boolean checkPhoto(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from tableimage where name =?", new String[]{user});
        return c.getCount() > 0;
    }
    public Bitmap getPhoto(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select image from tableimage where name =?", new String[]{user});
        Bitmap bm = null;
        if(c.getCount()>0){
            c.moveToFirst();
            byte [] byteArray = c.getBlob(0);
            bm = BitmapFactory.decodeByteArray(byteArray, 0 , byteArray.length);
            return bm;
        }else{
            return bm;
        }
    }
}