package com.bernalgas.finalchaval;

public class OurDataSet {
    String username;
    String password;
    String birthDate;
    String nationality;
    String email;

    public com.bernalgas.finalchaval.json getJson() {
        return json;
    }

    public void setJson(com.bernalgas.finalchaval.json json) {
        this.json = json;
    }

    json json;

    public OurDataSet(String username, String password, String birthDate, String nationality, String email) {
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.email = email;
    }

    public OurDataSet() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
