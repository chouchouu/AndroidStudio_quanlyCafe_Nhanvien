package com.example.project_android_duan.adapter.adapter.adapter_oder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android_duan.R;
import com.example.project_android_duan.model.model.model_oder.Oder_drink;
import com.example.project_android_duan.oder.Item_hoadonActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Oder_Drink extends RecyclerView.Adapter<Adapter_Oder_Drink.ViewHolder> {
    public List<Oder_drink> oder_drinkList;
    private Context context;
    private StorageReference storage = FirebaseStorage.getInstance().getReference();


    public Adapter_Oder_Drink(List<Oder_drink> oderDrinkList, Context context) {
        this.context = context;
        this.oder_drinkList = oderDrinkList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_oder_drink, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Oder_drink oder_drink = oder_drinkList.get(position);
//        holder.imv.s;
        holder.tvten.setText("Tên Món:" + " " + oder_drink.getName());
        holder.tvgia.setText("Giá Món:" + " " + String.valueOf(oder_drink.getPrice()));
        holder.tvmota.setText("Mô Tả:" + " " + oder_drink.getDescription());
        Picasso.with(context).load(oder_drink.getImage()).into(holder.imv);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Item_hoadonActivity.class);
                i.putExtra("no", oder_drink);
                context.startActivity(i);
            }
        });
        //Sửa
    }


    @Override
    public int getItemCount() {
        return oder_drinkList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imv;
        TextView tvgia, tvten, tvmota;
        CardView cardView;
        ImageButton img_update;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvten = itemView.findViewById(R.id.tvten);
            tvgia = itemView.findViewById(R.id.tvgia);
            tvmota = itemView.findViewById(R.id.tvmota);
            imv = itemView.findViewById(R.id.imv);
            cardView = itemView.findViewById(R.id.cardview);
            img_update = itemView.findViewById(R.id.img_update);


        }
    }


}



