package com.example.project_android_duan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Login_App_Activity extends AppCompatActivity {
    private ActionBar actionBar;
    ImageView header_icon;
    View login_container;
    Button btnnext,btncreate;
    TextView txtlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__app_);

        actionBar = getSupportActionBar();
//        actionBar.setTitle("CaFe");
        actionBar.hide();


        // Sự Kiện chuyển mainactivity
        overridePendingTransition(0,0);

        // Load animation
        Animation animation1 = AnimationUtils.loadAnimation(this,android.R.anim.fade_in);
        header_icon = findViewById(R.id.header_icon);

        // Sự Kiện Onclick
        btncreate = findViewById(R.id.btncreate);
        login_container = findViewById(R.id.login_container);
        login_container.startAnimation(animation1);
        btnnext=findViewById(R.id.btnnext);
        txtlogin=findViewById(R.id.btnloginApp);
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login_App_Activity.this,LoginAccount_Activity.class);
                startActivity(i);
            }
        });
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_App_Activity.this,Creat_Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}
