package com.example.project_android_duan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android_duan.HomeScreen_Activity;
import com.example.project_android_duan.R;
import com.example.project_android_duan.adapter.adapter.adapter_hoadon.Adapter_hoadon_drink;
import com.example.project_android_duan.adapter.adapter.adapter_oder.Adapter_Oder_Drink;
import com.example.project_android_duan.model.model.model_hoadon.hoadon;
import com.example.project_android_duan.model.model.model_oder.Oder_drink;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_ThongKe extends Fragment {
    RecyclerView rv_cart_drink;
    Adapter_hoadon_drink hoadon_drink;
    List<hoadon> hoadonList;
    DatabaseReference myRe = FirebaseDatabase.getInstance().getReference();
    Button btn_exit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_exit = view.findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), HomeScreen_Activity.class);
                startActivity(i);
            }
        });
        hoadonList = new ArrayList<>();
        rv_cart_drink = view.findViewById(R.id.rv_lichsu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_cart_drink.setLayoutManager(linearLayoutManager);
        rv_cart_drink.setHasFixedSize(true);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot data = dataSnapshot.child("HoaDon");
                hoadonList.clear();
                for (DataSnapshot valueclass : data.getChildren()) {
                    hoadon hd = valueclass.getValue(hoadon.class);

                    hoadon_drink = new Adapter_hoadon_drink(hoadonList, getContext());
                    hoadonList.add(hd);
                    rv_cart_drink.setAdapter(hoadon_drink);
                    hoadon_drink.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        myRe.addValueEventListener(eventListener);

    }
}
