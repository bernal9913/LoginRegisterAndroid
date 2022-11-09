package com.bernalgas.finalchaval;

public class User {
    public User(String username, String email, String password, String birthdate, String nationality) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.nationality = nationality;
    }

    String username;
    String email;
    String password;
    String birthdate;
    String nationality;
    String userType;



    public User(String user, String email, String password, String birthdate, String nationality, String userType) {
        this.username = user;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
