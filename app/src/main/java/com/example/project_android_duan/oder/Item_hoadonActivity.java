package com.example.project_android_duan.oder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_android_duan.HomeScreen_Activity;
import com.example.project_android_duan.R;
import com.example.project_android_duan.fragment.Fragment_Oder;
import com.example.project_android_duan.fragment.Fragment_Store;
import com.example.project_android_duan.model.model.model_giohang.GioHang;
import com.example.project_android_duan.model.model.model_hoadon.hoadon;
import com.example.project_android_duan.model.model.model_oder.Oder_drink;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Item_hoadonActivity extends AppCompatActivity {
    EditText ed_soluong;
    private TextView tv_name, tv_money, tv_mt, tvngaymua, tensp, giasp, soluongsp, tongtiensp, ngaymuasp;
    Button btn_chon, btn_giohangDrink;
    ImageView btn_huy;
    RecyclerView rv_item;
    Oder_drink oder_drink;
    private Intent intent;
    private List<hoadon> hoadonList;
    //Adapter_hoadon adapter_hd;
    int NumBer;
    hoadon hd;
    DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("HoaDon");
    GioHang gioHang;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("GioHang");
    String mahoadon = "";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String stringcurrentday;
    StorageReference my = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_hoadon);
        addEvent();
        Date c = Calendar.getInstance().getTime();
        stringcurrentday = simpleDateFormat.format(c);
        hoadonList = new ArrayList<>();

        //Hiện ra dữ liệu
        intent = getIntent();
        oder_drink = (Oder_drink) intent.getSerializableExtra("no");
        tv_name.setText(oder_drink.getName());
        tv_money.setText(oder_drink.getPrice() + "");
        tv_mt.setText(oder_drink.getDescription());
        tvngaymua.setText(stringcurrentday);

        final String tenspdrink = oder_drink.getName();
        final int giaspdrink = Integer.parseInt(oder_drink.getPrice() + "");
        btn_huy = findViewById(R.id.btn_huy);
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_chon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_soluong.length() == 0) {
                    Toast.makeText(Item_hoadonActivity.this, "Bạn Hãy Nhập Số Lượng", Toast.LENGTH_SHORT).show();
                } else {
                    int ToTal = Integer.parseInt(ed_soluong.getText().toString()) * oder_drink.getPrice();
                    HomeScreen_Activity.gioHangList.add(new GioHang(oder_drink.getName(), oder_drink.getKey(), oder_drink.getPrice(), Integer.parseInt(ed_soluong.getText().toString()), ToTal));

                    mahoadon = Ref.push().getKey();
                    hoadon hd = new hoadon(mahoadon, "", HomeScreen_Activity.gioHangList, stringcurrentday, ToTal);
                    Ref.child(mahoadon).setValue(hd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Item_hoadonActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            HomeScreen_Activity.gioHangList.clear();
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Toast.makeText(Item_hoadonActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }

                    });
                }

            }
        });

        btn_giohangDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_soluong.length() == 0) {
                    Toast.makeText(Item_hoadonActivity.this, "Mời Bạn Nhập Số Lượng", Toast.LENGTH_SHORT).show();
                } else {
                    int ToTal = Integer.parseInt(ed_soluong.getText().toString()) * oder_drink.getPrice();
                    HomeScreen_Activity.gioHangList.add(new GioHang(oder_drink.getName(), oder_drink.getKey(), oder_drink.getPrice(), Integer.parseInt(ed_soluong.getText().toString()), ToTal));
                    finish();
                }
            }
        });


    }

    private void addEvent() {
        ed_soluong = findViewById(R.id.ed_soluong);
        btn_chon = findViewById(R.id.btn_chon);
        btn_giohangDrink = findViewById(R.id.btn_giohangDrink);
        tv_money = findViewById(R.id.tv_money);
        tv_mt = findViewById(R.id.tv_mt);
        tv_name = findViewById(R.id.tv_name);
        tvngaymua = findViewById(R.id.tvngaymua);

        tensp = findViewById(R.id.tensp);
        giasp = findViewById(R.id.giasp);
        soluongsp = findViewById(R.id.soluongsp);
        tongtiensp = findViewById(R.id.tongtiensp);
        ngaymuasp = findViewById(R.id.ngaymuasp);

    }
}
