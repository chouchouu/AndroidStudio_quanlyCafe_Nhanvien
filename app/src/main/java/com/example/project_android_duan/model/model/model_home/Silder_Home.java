package com.example.project_android_duan.model.model.model_home;

public class Silder_Home {
    private String title;
    private String txt2;
    private int image;

    public Silder_Home() {
    }

    public Silder_Home(String title, String txt2, int image) {
        this.title = title;
        this.txt2 = txt2;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxt2() {
        return txt2;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
