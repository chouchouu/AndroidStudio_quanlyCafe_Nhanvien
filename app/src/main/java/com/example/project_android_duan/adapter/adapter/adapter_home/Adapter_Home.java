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
import com.example.project_android_duan.model.model.model_home.Silder_Home;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Home extends RecyclerView.Adapter<Adapter_Home.ViewHolder> {
    private List<Silder_Home> homeList;
    private Context context;


    public Adapter_Home(ArrayList<Silder_Home> homeList , Context context){
            this.context = context;
            this.homeList = homeList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_homestore,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Silder_Home silder_home = homeList.get(position);
        holder.image_home.setImageResource(silder_home.getImage());
        holder.title_home.setText(silder_home.getTitle());
        holder.txt2.setText(silder_home.getTxt2());
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_home;
        TextView title_home,txt1,txt2;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
          image_home = itemView.findViewById(R.id.image_home);
          title_home = itemView.findViewById(R.id.title_home);
          txt1 = itemView.findViewById(R.id.txt1);
          txt2 = itemView.findViewById(R.id.txt2);
        }
    }
}
