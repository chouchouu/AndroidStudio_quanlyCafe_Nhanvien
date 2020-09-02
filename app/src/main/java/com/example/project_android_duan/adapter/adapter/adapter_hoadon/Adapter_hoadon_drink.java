package com.example.project_android_duan.adapter.adapter.adapter_hoadon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android_duan.ChitietDonHang_Activity;
import com.example.project_android_duan.R;
import com.example.project_android_duan.model.model.model_giohang.GioHang;
import com.example.project_android_duan.model.model.model_hoadon.hoadon;

import java.util.List;

public class Adapter_hoadon_drink extends RecyclerView.Adapter<Adapter_hoadon_drink.ViewHolder> {
    public List<hoadon> hoadonList;
    private Context context;


    public Adapter_hoadon_drink(List<hoadon> hoadonList, Context context) {
        this.hoadonList = hoadonList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_hoadon_drink, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final hoadon hd = hoadonList.get(position);
        holder.tv_mahoadon.setText(hd.getMahoadon());
        holder.tv_ngaymua.setText(hd.getDate());
        holder.tv_total.setText(String.valueOf(hd.getTotal()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ChitietDonHang_Activity.class);
                i.putExtra("key", hd.getMahoadon());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hoadonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView tv_mahoadon, tv_ngaymua, tv_total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_mahoadon = itemView.findViewById(R.id.tv_mahoadon);
            tv_ngaymua = itemView.findViewById(R.id.tv_ngaymua);
            tv_total = itemView.findViewById(R.id.tv_total);
            cardView = itemView.findViewById(R.id.card_hoadon_drink);
        }
    }
}
