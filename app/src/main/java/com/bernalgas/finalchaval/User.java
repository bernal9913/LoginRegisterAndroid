package com.bernalgas.finalchaval;

public class User {
    String user;
    String email;
    String password;
    String birthdate;
    String nationality;

    public User(String user, String email, String password, String birthdate, String nationality) {
        this.user = user;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.nationality = nationality;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
