package com.example.project_android_duan.model.model.model_account;

import java.io.Serializable;

public class Account implements Serializable  {
    private String key;
    private String imageURL;
    private String username;
    private String email;
    private String    phone;
    private String chucvu;

    public Account() {

    }

    public Account(String key, String imageURL, String username, String email, String phone, String chucvu) {
        this.key = key;
        this.imageURL = imageURL;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.chucvu = chucvu;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }
}
