package com.example.project_android_duan.model.model.model_oder;

import java.io.Serializable;

public class Oder_drink implements Serializable {
    private String Key;
    private String Name;
    private String Description;
    private int Price;
    private String Image;


    public Oder_drink() {
    }

    public Oder_drink(String key, String name, String description, int price, String image) {
        Key = key;
        Name = name;
        Description = description;
        Price = price;
        Image = image;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
