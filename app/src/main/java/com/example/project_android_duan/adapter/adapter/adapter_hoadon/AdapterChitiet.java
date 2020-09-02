package com.example.project_android_duan.adapter.adapter.adapter_hoadon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_android_duan.R;
import com.example.project_android_duan.model.model.model_giohang.GioHang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterChitiet extends ArrayAdapter<GioHang> {
    Activity context;
    int resource;
    List<GioHang> objects;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public AdapterChitiet(Activity context, int resource, List<GioHang> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = this.context.getLayoutInflater();
            convertView = inflater.inflate(this.resource, null);
            viewHolder.tv_tenmonan = convertView.findViewById(R.id.tv_tensanpham);
            viewHolder.tv_soluong = convertView.findViewById(R.id.tv_soluong);
            viewHolder.tv_giatien = convertView.findViewById(R.id.tv_total);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final GioHang gioHang = this.objects.get(position);

        viewHolder.tv_tenmonan.setText(gioHang.getName());
        viewHolder.tv_soluong.setText(String.valueOf(gioHang.getNumber()));
        viewHolder.tv_giatien.setText(String.valueOf(gioHang.getTotal()));


        return convertView;
    }

    static class ViewHolder {
        TextView tv_tenmonan, tv_soluong, tv_giatien;
    }
}
