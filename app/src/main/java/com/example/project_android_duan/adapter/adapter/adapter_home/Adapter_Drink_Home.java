package com.example.project_android_duan.adapter.adapter.adapter_home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android_duan.R;
import com.example.project_android_duan.model.model.model_home.DrinkHome;

import java.util.List;

public class Adapter_Drink_Home extends RecyclerView.Adapter<Adapter_Drink_Home.ViewHolder> {
    private List<DrinkHome> drinkHomeList;
    private Context context;

    public Adapter_Drink_Home(List<DrinkHome> drinkHomeList, Context context){
        this.context = context;
        this.drinkHomeList = drinkHomeList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardivew_drink_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrinkHome drinkHome = drinkHomeList.get(position);
        holder.drink_name.setText(drinkHome.getDrink_name());
        holder.drink_price.setInputType(drinkHome.getDrink_price());
        holder.drink_image.setImageResource(drinkHome.getDrink_image());
    }

    @Override
    public int getItemCount() {
        return drinkHomeList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        TextView  drink_name, drink_price;
        ImageView drink_image;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            drink_image = itemView.findViewById(R.id.drink_image);
            drink_name = itemView.findViewById(R.id.drink_name);
            drink_price = itemView.findViewById(R.id.drink_price);
        }
    }
}
