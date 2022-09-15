package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbStopJumper extends SQLiteOpenHelper {
    public static final String DBNAME = "login.db";
    public dbStopJumper(Context context) {
        super(context, "login.db", null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE users(username text PRIMARY KEY, email text, password text, birthdate text, nacion text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists users");
    }
    public boolean insertData(String u, String p, String e, String b, String n){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", u);
        values.put("password", p);
        values.put("email", e);
        values.put("birthdate", b);
        values.put("nacion", n);
        long result = db.insert("users", null, values);
        if(result ==-1) return false;
        else {
            return true;
        }
    }
    public Boolean checkUser(String u ){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from users where username =?", new String[] {u});
        if(c.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean checkUserPass(String u, String p){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from users where username =? and password=?", new String[] {u,p});
        if(c.getCount()>0)
            return true;
        else
            return false;
    }
}
