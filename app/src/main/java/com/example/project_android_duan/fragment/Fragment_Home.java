package com.example.project_android_duan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android_duan.R;
import com.example.project_android_duan.adapter.adapter.adapter_home.Adapter_Drink_Home;
import com.example.project_android_duan.adapter.adapter.adapter_home.Adapter_Home;
import com.example.project_android_duan.model.model.model_home.DrinkHome;
import com.example.project_android_duan.model.model.model_home.Silder_Home;

import java.util.ArrayList;

public class Fragment_Home extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        // Review Srotre

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<Silder_Home> arrayList = new ArrayList<>();
        arrayList.add(new Silder_Home("Accessoris","Oder",R.drawable.image));
        arrayList.add(new Silder_Home("Aqua Coffea","Amazing",R.drawable.imageeeee));
        arrayList.add(new Silder_Home("Jones & Burke","Drink",R.drawable.imageee));
        arrayList.add(new Silder_Home("Ekko","Montage",R.drawable.imageeee));
        Adapter_Home adapter_home = new Adapter_Home(arrayList,getActivity());
        recyclerView.setAdapter(adapter_home);

        // Review Drink

        RecyclerView recyclerView_drink = view.findViewById(R.id.recyclerview_drink);
        LinearLayoutManager linearLayoutManager_drink = new LinearLayoutManager(getActivity());
        linearLayoutManager_drink.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_drink.setLayoutManager(linearLayoutManager_drink);
        recyclerView_drink.setItemAnimator(new DefaultItemAnimator());
        ArrayList<DrinkHome> arrayList_drink = new ArrayList<>();
        arrayList_drink.add(new DrinkHome(R.drawable.mocha,"MoCha",50000));
        arrayList_drink.add(new DrinkHome(R.drawable.cappuccino,"Cappuccino",50000));
        arrayList_drink.add(new DrinkHome(R.drawable.cafephinden,"Coffee ",50000));
        arrayList_drink.add(new DrinkHome(R.drawable.coffeemike,"Coffee Mike",50000));
        Adapter_Drink_Home adapter_drink_home = new Adapter_Drink_Home(arrayList_drink,getActivity());
        recyclerView_drink.setAdapter(adapter_drink_home);


        return  view;
    }
}
