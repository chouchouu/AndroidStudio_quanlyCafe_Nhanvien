package com.example.project_android_duan.adapter.adapter.adapter_hoadon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android_duan.R;
import com.example.project_android_duan.fragment.Fragment_Oder;
import com.example.project_android_duan.fragment.Fragment_Store;
import com.example.project_android_duan.model.model.model_giohang.GioHang;

import java.util.List;
import java.util.zip.Inflater;

public class Adapter_giohang_drink extends RecyclerView.Adapter<Adapter_giohang_drink.ViewHolder> {
    public List<GioHang> gioHangList;
    public Context context;

    public Adapter_giohang_drink(List<GioHang> gioHangList, Context context) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_giohang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GioHang gioHang = gioHangList.get(position);
        holder.tv_tensanpham.setText(gioHang.getName());
        holder.tv_soluong.setText(String.valueOf(gioHang.getNumber()));
        holder.tv_total.setText(String.valueOf(gioHang.getTotal()));

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // code xóa món ăn
                return true;
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fragment_Store.update_soluong(gioHang, context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tv_tensanpham, tv_soluong, tv_total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tensanpham = itemView.findViewById(R.id.tv_tensanpham);
            tv_soluong = itemView.findViewById(R.id.tv_soluong);
            tv_total = itemView.findViewById(R.id.tv_total);
            cardView = itemView.findViewById(R.id.card_hoadon_drink);
        }
    }
}
