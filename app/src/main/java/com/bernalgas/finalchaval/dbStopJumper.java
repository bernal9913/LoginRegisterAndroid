package com.bernalgas.finalchaval;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbStopJumper extends SQLiteOpenHelper {
    public static final String DBNAME = "usersDP.db";
    public dbStopJumper(Context context) {
        super(context, "usersDP.db", null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE usersdp(username text PRIMARY KEY, dp text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists usersdp");
    }
    public boolean insertDisplayPicture(String u, String dp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", u);
        values.put("dp", dp);
        long result = db.insert("users", null, values);
        if(result ==-1) return false;
        else {
            return true;
        }
    }
    public String checkUser(String u) {
        SQLiteDatabase db = this.getWritableDatabase();
        String displayPicture;
        Cursor c = db.rawQuery("SELECT dp FROM usersdp WHERE user =?", new String[]{u});
        if(c.moveToFirst()){
            displayPicture = c.getString(0);
        }else{
            displayPicture = "NONE";
        }
        return displayPicture;
    }
    public boolean modDisplayPicture(String u, String dp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dp", dp);
        int result = db.update("users", values, "_username = ?", new String[]{u});
        if(result ==-1) return false;
        else{
            return true;
        }
    }
}