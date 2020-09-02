package com.example.project_android_duan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.project_android_duan.fragment.Fragment_Account;
import com.example.project_android_duan.fragment.Fragment_Home;
import com.example.project_android_duan.fragment.Fragment_Oder;
import com.example.project_android_duan.fragment.Fragment_Store;
import com.example.project_android_duan.fragment.Fragment_ThongKe;
import com.example.project_android_duan.model.model.model_giohang.GioHang;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen_Activity extends AppCompatActivity {
    BottomNavigationView bottom_nav;
    ActionBar actionBar;
    public static List<GioHang> gioHangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_);


        bottom_nav = findViewById(R.id.botom_nav);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
                bottom_nav.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        //ActionBar
        actionBar = getSupportActionBar();


        // Cho Màn Hình mặc định khi vào app là Home
        if (savedInstanceState == null) {
            actionBar.hide();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,
                    new Fragment_Home()).commit();
            bottom_nav.setSelectedItemId(R.id.home);
        }


        // Bắt Các Sự Kiện OnClick Item với Fragment
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        actionBar.hide();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,
                                new Fragment_Home()).commit();
                        return true;
                    case R.id.sotre:
                        actionBar.hide();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,
                                new Fragment_Store()).commit();
                        return true;
                    case R.id.oder:
                        actionBar.hide();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,
                                new Fragment_Oder()).commit();
                        return true;
                    case R.id.statistical:
                        actionBar.hide();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,
                                new Fragment_ThongKe()).commit();
                        return true;
                    case R.id.account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,
                                new Fragment_Account()).commit();
                        return true;
                }
                return false;
            }
        });


        //xử lý giỏ hàng
        if (gioHangList != null) {

        } else {
            gioHangList = new ArrayList<>();
        }
    }
}
