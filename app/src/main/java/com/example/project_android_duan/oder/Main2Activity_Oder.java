package com.example.project_android_duan.oder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_android_duan.HomeScreen_Activity;
import com.example.project_android_duan.R;
import com.example.project_android_duan.fragment.Fragment_ThongKe;
import com.example.project_android_duan.model.model.model_hoadon.hoadon;
import com.example.project_android_duan.model.model.model_oder.Oder_drink;

public class Main2Activity_Oder extends AppCompatActivity {
    TextView ten, soluong, ngay, gia;
    Button btnthanhtoan;
    Oder_drink oder_drink;
    String stringcurrentday;
    private Intent i;
    hoadon hd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__oder);
        addEvent();
//        i = getIntent();
//        hd = (hoadon) i.getSerializableExtra("hoadon");
//        ten.setText(hd.getName());
//        gia.setText(hd.getTotal() + "");
//        soluong.setText(hd.getNumber() + "");
//        ngay.setText(hd.getDate());
//        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });


//        intent = getIntent();
//        oder_drink = (Oder_drink) intent.getSerializableExtra("no");
//        final String tenspdrink = oder_drink.getName();
////        final int giaspdrink = Integer.parseInt(oder_drink.getPrice()+"");
//        intent = getIntent();
//        ten.setText("Name:"+""+hd.getName());
//        gia.setText("Price:"+""+hd.getPrice());
//
//
//
////        final int numBer =Integer.parseInt(.getText().toString());
////        final int tongtiendrink = Integer.parseInt(String.valueOf(oder_drink.getPrice()*numBer));
////        ten.setText("Name:"+""+tenspdrink);
//////        g.setText("Price:"+""+giaspdrink+"");
//        gia.setText("Sum:"+""+tongtiendrink+"");
//        ngay.setText("Date:"+""+stringcurrentday);
////        soluong.setText("Number:"+""+numBer+"");
    }

    private void addEvent() {
        ten = findViewById(R.id.tvten2);
        gia = findViewById(R.id.tvgia2);
        soluong = findViewById(R.id.tvgia1);
        ngay = findViewById(R.id.tvngay);
        btnthanhtoan = findViewById(R.id.btn_thanhtoan2);
    }
}
