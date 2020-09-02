package com.example.project_android_duan.model.model.model_giohang;

public class GioHang {
    private String Name;
    private String key;
    private int Price;
    private int Number;
    private int Total;

    public GioHang() {
    }

    public GioHang(String name, String key, int price, int number, int total) {
        Name = name;
        this.key = key;
        Price = price;
        Number = number;
        Total = total;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
