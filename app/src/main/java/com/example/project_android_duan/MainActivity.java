package com.example.project_android_duan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private boolean isFirstAnimation = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
//        actionBar.setTitle("CaFe");
        actionBar.hide();



        // Giữ cho  hình ở vị trí giữa màn hình so với vọ trí hiện tại
        Animation hold = AnimationUtils.loadAnimation(this, R.anim.hold);
        final Animation translateScale = AnimationUtils.loadAnimation(this, R.anim.translate_scale);

        final ImageView imageView = findViewById(R.id.header_icon);
        translateScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            // Bắt Sự kiện sau khoản thời gian kết thúc animation của translateScale
            // thì chuyển qua mainactivity mới
            public void onAnimationEnd(Animation animation) {
                if (!isFirstAnimation) {
                    imageView.clearAnimation();
                    Intent intent = new Intent(MainActivity.this, Login_App_Activity.class);
                    startActivity(intent);
                    finish();
                }

                isFirstAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hold.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            // Bắt Sự Kiện khi animation của hold kết thúc sau khoản thời gian mặc định
            // thì bắt đầu với animatino của translateScale
            // hình trở về vị trí ban đầu mặc định của nó
            public void onAnimationEnd(Animation animation) {
                imageView.clearAnimation();
                imageView.startAnimation(translateScale);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // Dùng animation để hình gán ở giữa màn hình so với vị trí ban đầu
        imageView.startAnimation(hold);


    }


    }



