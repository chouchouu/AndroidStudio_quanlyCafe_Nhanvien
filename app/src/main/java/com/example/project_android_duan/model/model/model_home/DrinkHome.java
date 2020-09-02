package com.example.project_android_duan.model.model.model_home;

public class DrinkHome {
    int drink_image;
    String drink_name;
    int drink_price;

    public DrinkHome() {
    }

    public DrinkHome(int drink_image, String drink_name, int drink_price) {
        this.drink_image = drink_image;
        this.drink_name = drink_name;
        this.drink_price = drink_price;
    }

    public int getDrink_image() {
        return drink_image;
    }

    public void setDrink_image(int drink_image) {
        this.drink_image = drink_image;
    }

    public String getDrink_name() {
        return drink_name;
    }

    public void setDrink_name(String drink_name) {
        this.drink_name = drink_name;
    }

    public int getDrink_price() {
        return drink_price;
    }

    public void setDrink_price(int drink_price) {
        this.drink_price = drink_price;
    }
}
