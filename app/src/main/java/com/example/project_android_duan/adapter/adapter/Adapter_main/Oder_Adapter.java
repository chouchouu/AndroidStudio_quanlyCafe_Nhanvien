package com.example.project_android_duan.adapter.adapter.Adapter_main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.project_android_duan.fragment.Fragment_oder_drink;
import com.example.project_android_duan.fragment.Fragment_oder_food;

public class Oder_Adapter extends FragmentPagerAdapter {


    public Oder_Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                Fragment_oder_drink drink = new Fragment_oder_drink();
                return drink;
            case 1:
                Fragment_oder_food food = new Fragment_oder_food();
                return food;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title ="Thức Uống";
                break;
            case 1:
                title="Đồ Ăn";

        }
        return title;
    }
}
