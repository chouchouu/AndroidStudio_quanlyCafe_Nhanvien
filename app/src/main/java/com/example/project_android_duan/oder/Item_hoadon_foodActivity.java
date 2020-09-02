package com.example.project_android_duan.oder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_android_duan.HomeScreen_Activity;
import com.example.project_android_duan.R;
import com.example.project_android_duan.model.model.model_giohang.GioHang;
import com.example.project_android_duan.model.model.model_hoadon.hoadon;
import com.example.project_android_duan.model.model.model_oder.Oder_drink;
import com.example.project_android_duan.model.model.model_oder.Oder_food;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Item_hoadon_foodActivity extends AppCompatActivity {
    EditText ed_soluong;
    private TextView tv_name, tv_money, tv_mt, tvngaymua, tensp, giasp, soluongsp, tongtiensp, ngaymuasp;
    Button btn_chon, btn_giohang_food;
    ImageView btn_huy;

    Oder_food oder_food;
    private Intent intent;
    private List<hoadon> hoadonList;
    DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("HoaDon");
    String mahoadon = "";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String stringcurrentday;


    DatabaseReference database = FirebaseDatabase.getInstance().getReference("GioHang");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_hoadon_food);
        addEvent();
        btn_huy = findViewById(R.id.btn_cancelPass);
        Date c = Calendar.getInstance().getTime();
        stringcurrentday = simpleDateFormat.format(c);
        hoadonList = new ArrayList<>();

        //Hiện ra dữ liệu
        intent = getIntent();
        oder_food = (Oder_food) intent.getSerializableExtra("yes");
        tv_name.setText(oder_food.getName());
        tv_money.setText(oder_food.getPrice() + "");
        tv_mt.setText(oder_food.getDescription());
        tvngaymua.setText(stringcurrentday);

        final String tenspdrink = oder_food.getName();
        final int giaspdrink = Integer.parseInt(oder_food.getPrice() + "");
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
                    Toast.makeText(Item_hoadon_foodActivity.this, "Bạn Hãy Nhập Số Lượng", Toast.LENGTH_SHORT).show();
                } else {
                    int ToTal = Integer.parseInt(ed_soluong.getText().toString()) * oder_food.getPrice();
                    HomeScreen_Activity.gioHangList.add(new GioHang(oder_food.getName(), oder_food.getKey(), oder_food.getPrice(), Integer.parseInt(ed_soluong.getText().toString()), ToTal));

                    mahoadon = Ref.push().getKey();
                    hoadon hd = new hoadon(mahoadon, "", HomeScreen_Activity.gioHangList, stringcurrentday, ToTal);
                    Ref.child(mahoadon).setValue(hd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Item_hoadon_foodActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            HomeScreen_Activity.gioHangList.clear();
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Toast.makeText(Item_hoadon_foodActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }

                    });
                }


                //tam thoi tắt cai nay nhe, mở lại sau
//                    Intent i = new Intent(Item_hoadon_foodActivity.this, Main2Activity_Oder.class);
//                    i.putExtra("hoadon", hd);
//                    startActivity(i);
//                    tensp.setText("Name:" + "" + tenspdrink);
//                    giasp.setText("Price:" + "" + giaspdrink + "");
//                    tongtiensp.setText("Sum:" + "" + tongtiendrink + "");
//                    ngaymuasp.setText("Date:" + "" + stringcurrentday);
//                    soluongsp.setText("Number:" + "" + numBer + "");

            }
        });

        btn_giohang_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_soluong.length() == 0) {
                    Toast.makeText(Item_hoadon_foodActivity.this, "Mời Bạn Nhập Số Lượng", Toast.LENGTH_SHORT).show();
                } else {
                    int ToTal = Integer.parseInt(ed_soluong.getText().toString()) * oder_food.getPrice();
                    HomeScreen_Activity.gioHangList.add(new GioHang(oder_food.getName(), oder_food.getKey(), oder_food.getPrice(), Integer.parseInt(ed_soluong.getText().toString()), ToTal));
                    finish();
                }
            }
        });

    }

    private void addEvent() {
        ed_soluong = findViewById(R.id.ed_soluong);
        btn_chon = findViewById(R.id.btn_chon);
        btn_giohang_food = findViewById(R.id.btn_giohang_food);
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
