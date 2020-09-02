package com.example.project_android_duan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_android_duan.fragment.Fragment_Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAccount_Activity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ActionBar actionBar;
    TextView txtlogin;
    EditText email, password;
    Button btnnext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_account_);
        actionBar = getSupportActionBar();
        actionBar.hide();

        // Sự kiện Onclick

        btnnext = findViewById(R.id.btnnext);
        email = findViewById(R.id.email);
        password = findViewById(R.id.passsword);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dannhap();
            }
        });
        txtlogin = findViewById(R.id.txtlogin);
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAccount_Activity.this,Creat_Login_Activity.class);
                startActivity(intent);
            }
        });
    }
     // bắt sự kiện và check lỗi đăng nhập tài khoản
    private void Dannhap() {
        final String edt_email = email.getText().toString();
        final String edt_pass = password.getText().toString();

        if (edt_pass.isEmpty() || edt_pass.isEmpty()){
            Toast.makeText(this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(edt_email,edt_pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent1 = new Intent(LoginAccount_Activity.this,HomeScreen_Activity.class);
                                startActivity(intent1);
                                Toast.makeText(LoginAccount_Activity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(LoginAccount_Activity.this, "Đăng Nhập Thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        }
            });
        }

    }
    }

