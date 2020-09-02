package com.example.project_android_duan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.project_android_duan.HomeScreen_Activity;
import com.example.project_android_duan.R;
import com.example.project_android_duan.adapter.adapter.Adapter_main.Oder_Adapter;
import com.example.project_android_duan.adapter.adapter.adapter_oder.Adapter_Oder_Drink;
import com.example.project_android_duan.adapter.adapter.adapter_oder.Adapter_Oder_Food;
import com.example.project_android_duan.model.model.model_oder.Oder_drink;
import com.example.project_android_duan.model.model.model_oder.Oder_food;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Fragment_Oder extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Oder_Adapter oder_adapter;
    ImageView img_back;
    int number = 2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oder,container,false);

        img_back=view.findViewById(R.id.img_back);
        tabLayout=view.findViewById(R.id.tablayout);
        viewPager=view.findViewById(R.id.pager);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        oder_adapter = new Oder_Adapter(getChildFragmentManager());
        viewPager.setAdapter(oder_adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        //Nút Back
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), HomeScreen_Activity.class);
                startActivity(i);
            }
        });
//        RecyclerView recyclerView_oder_drink = view.findViewById(R.id.recyclerview_oder_drink);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),number);
//        recyclerView_oder_drink.setLayoutManager(gridLayoutManager);
//        recyclerView_oder_drink.setItemAnimator(new DefaultItemAnimator());
//        ArrayList<Oder_drink> arrayList = new ArrayList<>();
//        arrayList.add(new Oder_drink("Cappuccino","Double espresso",50000,R.drawable.capuchi));
//        arrayList.add(new Oder_drink("Latte","Espresso, Milk",50000,R.drawable.latte));
//        arrayList.add(new Oder_drink("Espresso","Double espresso",50000,R.drawable.cafephinden));
//        arrayList.add(new Oder_drink("Americano","Espresso,Hot water",50000,R.drawable.coffeemike));
//        arrayList.add(new Oder_drink("Mocha","Chocolate",50000,R.drawable.mocha));
//        arrayList.add(new Oder_drink("Hot Chocolate","Chocolate, Milk",50000,R.drawable.cappuccino));
//        Adapter_Oder_Drink adapter_oder_drink = new Adapter_Oder_Drink(arrayList,getContext());
//        recyclerView_oder_drink.setAdapter(adapter_oder_drink);
//
//
//        RecyclerView recyclerView_oder_food = view.findViewById(R.id.recyclerview_oder_food);
//        GridLayoutManager gridLayoutManager_food = new GridLayoutManager(getContext(),number);
//        recyclerView_oder_food.setLayoutManager(gridLayoutManager_food);
//        recyclerView_oder_drink.setItemAnimator(new DefaultItemAnimator());
//        ArrayList<Oder_food> oderFoodArrayList = new ArrayList<>();
//        oderFoodArrayList.add(new Oder_food("Mochi Matcha","Cream",50000,R.drawable.cake6));
//        oderFoodArrayList.add(new Oder_food("Matcha Mille Crepe","Cream",50000,R.drawable.cake1));
//        oderFoodArrayList.add(new Oder_food("Matcha Outlet","Cream",50000,R.drawable.cake4));
//        oderFoodArrayList.add(new Oder_food("Matcha Eologist","Cream",50000,R.drawable.cake3));
//        oderFoodArrayList.add(new Oder_food("Matcha","Cream",50000,R.drawable.cake2));
//        oderFoodArrayList.add(new Oder_food("Matcha Cream","Cream",50000,R.drawable.cake5));
//        Adapter_Oder_Food adapter_oder_food = new Adapter_Oder_Food(oderFoodArrayList,getContext());
//        recyclerView_oder_food.setAdapter(adapter_oder_food);




        return view;
    }
}
