package com.example.project_android_duan.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android_duan.HomeScreen_Activity;
import com.example.project_android_duan.R;
import com.example.project_android_duan.adapter.adapter.adapter_hoadon.Adapter_giohang_drink;
import com.example.project_android_duan.adapter.adapter.adapter_hoadon.Adapter_hoadon_drink;
import com.example.project_android_duan.model.model.model_giohang.GioHang;
import com.example.project_android_duan.model.model.model_hoadon.hoadon;
import com.example.project_android_duan.model.model.model_oder.Oder_drink;
import com.example.project_android_duan.model.model.model_oder.Oder_food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Fragment_Store extends Fragment {
    RecyclerView rv_giohang;
    Button btn_tongtien, btn_thanhtoangiohang;
    TextView txt_tongtien;
    int Total;
    private GioHang gioHang = new GioHang();
    private Oder_food oder_food = new Oder_food();
    private Oder_drink oder_drink = new Oder_drink();
    private static Adapter_giohang_drink adapter_giohang_drink;
    private static ArrayList<GioHang> gioHangList = new ArrayList<>();
    private static DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String stringcurrentday;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        rv_giohang = view.findViewById(R.id.cardview_giohang);
        btn_tongtien = view.findViewById(R.id.btn_tongtien);
        btn_thanhtoangiohang = view.findViewById(R.id.btn_thanhtoangiohang);
        txt_tongtien = view.findViewById(R.id.edt_tongtien);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_giohang.setLayoutManager(linearLayoutManager);
        rv_giohang.setItemAnimator(new DefaultItemAnimator());
        rv_giohang.setHasFixedSize(true);
        Date c = Calendar.getInstance().getTime();

        stringcurrentday = simpleDateFormat.format(c);

        btn_tongtien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < HomeScreen_Activity.gioHangList.size(); i++) {
                    Total += HomeScreen_Activity.gioHangList.get(i).getTotal();
                }
                txt_tongtien.setText("Tổng tiền của bạn là: " + Total + " VND");
            }
        });

        btn_thanhtoangiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeScreen_Activity.gioHangList.size() == 0) {
                    Toast.makeText(getActivity(), "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < HomeScreen_Activity.gioHangList.size(); i++) {
                        Total += HomeScreen_Activity.gioHangList.get(i).getTotal();
                    }
                    String id = database.push().getKey();
                    hoadon hoadon = new hoadon(id, "", HomeScreen_Activity.gioHangList, stringcurrentday, Total);
                    database.child("HoaDon").child(id).setValue(hoadon).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            HomeScreen_Activity.gioHangList.clear();
                            adapter_giohang_drink = new Adapter_giohang_drink(HomeScreen_Activity.gioHangList, getActivity());
                            rv_giohang.setAdapter(adapter_giohang_drink);
                            Toast.makeText(getContext(), "Thanh Toán Thành Công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        adapter_giohang_drink = new Adapter_giohang_drink(HomeScreen_Activity.gioHangList, getActivity());
        rv_giohang.setAdapter(adapter_giohang_drink);


        return view;
    }

    public static void DiaLogDelete(final GioHang gioHang, final Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Thông Báo");
        dialog.setMessage("Bạn Có Muốn Xóa Không ?");
        dialog.setNeutralButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                gioHangList.clear();
                adapter_giohang_drink.notifyDataSetChanged();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                Query query = databaseReference.child("GioHang").orderByChild("name").equalTo(gioHang.getName());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot date : dataSnapshot.getChildren()) {
                            date.getRef().removeValue();
                            Toast.makeText(context, "Xóa Sản Phẩm Khỏi Giỏ Hàng", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                gioHangList.clear();


            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void update_soluong(final GioHang gioHang, final Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.update_soluongmua);
        final EditText soluong_muahang = dialog.findViewById(R.id.soluong_muahang);
        Button btn_thaydoi = dialog.findViewById(R.id.btn_thaydoi);
        int soluong = gioHang.getNumber();
        soluong_muahang.setText(soluong + "");

//        btn_thaydoi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int soluongmua = Integer.parseInt(soluong_muahang.getText().toString());
//                HashMap<String,Object> update = new HashMap<>();
//                update.put("number",soluongmua);
//
//                DatabaseReference datebase = FirebaseDatabase.getInstance().getReference("" +
//                        "");
//
//                datebase.updateChildren(update, new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                        Toast.makeText(context, "Thay Đổi Thành Công", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });
        dialog.show();
    }

}
