package com.bernalgas.finalchaval;

import android.graphics.Bitmap;

public class PhotoUser {

    String username;
    Bitmap photo;

    public PhotoUser(String username, Bitmap photo) {
        this.username = username;
        this.photo = photo;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getDp() {
        return photo;
    }

    public void setDp(Bitmap photo) {
        this.photo = photo;
    }
}
