package com.example.project_android_duan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class Creat_Login_Activity extends AppCompatActivity {
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat__login_);

        actionBar = getSupportActionBar();
//        actionBar.setTitle("CaFe");
        actionBar.hide();

        // Các Sự Kiện Đăng kí Thành Viên , Admin
    }
}
