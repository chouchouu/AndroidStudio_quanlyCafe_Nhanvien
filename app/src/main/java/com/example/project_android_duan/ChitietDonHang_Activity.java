package com.example.project_android_duan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_android_duan.adapter.adapter.adapter_hoadon.AdapterChitiet;
import com.example.project_android_duan.adapter.adapter.adapter_hoadon.Adapter_giohang_drink;
import com.example.project_android_duan.model.model.model_giohang.GioHang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChitietDonHang_Activity extends AppCompatActivity {
    Button btn_back;
    ListView lv_giohang;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    List<GioHang> gioHangList = new ArrayList<>();
    AdapterChitiet adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_don_hang_);

        //anh xa
        btn_back = findViewById(R.id.btn_back);
        lv_giohang = findViewById(R.id.lv_giohang);
        String key = getIntent().getStringExtra("key");
        //Toast.makeText(this, key, Toast.LENGTH_SHORT).show();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mData.child("HoaDon").child(key).child("gioHangList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gioHangList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    GioHang gioHang = data.getValue(GioHang.class);
                    gioHangList.add(gioHang);
                }

                adapter = new AdapterChitiet(ChitietDonHang_Activity.this, R.layout.cardview_giohang, gioHangList);
                lv_giohang.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
