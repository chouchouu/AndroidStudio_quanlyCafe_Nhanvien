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
import com.example.project_android_duan.model.model.model_oder.Oder_food;
import com.example.project_android_duan.oder.Item_hoadon_foodActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Oder_Food extends RecyclerView.Adapter<Adapter_Oder_Food.ViewHolder> {
    private List<Oder_food> oder_foodList;
    private Context context;
    public Adapter_Oder_Food(List<Oder_food> oder_foodList, Context context){
        this.context = context;
        this.oder_foodList = oder_foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_oder_food,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Oder_food oder_food = oder_foodList.get(position);
//        holder.imv.setImageResource(oder_food.getImage());
        holder.tvten.setText("Tên Món:"+" "+oder_food.getName());
        holder.tvgia.setText("Giá Món:"+" "+String.valueOf(oder_food.getPrice()));
        holder.tvmota.setText("Mô Tả:"+ " "+oder_food.getDescription());
        Picasso.with(context).load(oder_food.getImage()).into(holder.imv);
        holder.cardViewfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(context,Item_hoadon_foodActivity.class);
                i.putExtra("yes",oder_food);
                context.startActivity(i);
            }
        });
//        holder.cardViewfood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, MainActivity_Oder.class);
//                context.startActivity(intent);
//            }
//        });
//        holder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i =new Intent(context, Chang_foodActivity.class);
//                context.startActivity(i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return oder_foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imv;
        ImageButton img;
        TextView tvgia,tvten,tvmota;
        CardView cardView,cardViewfood;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvten=itemView.findViewById(R.id.tvten_food);
//            img=itemView.findViewById(R.id.imv_food);
            tvgia=itemView.findViewById(R.id.tvgia_food);
            tvmota=itemView.findViewById(R.id.tvmota_food);
            imv=itemView.findViewById(R.id.imv_food);
            cardView=itemView.findViewById(R.id.cardview);
            cardViewfood=itemView.findViewById(R.id.cardview_food);



        }
    }
//    public void removeItem(int position){
//        oder_foodList.remove(position);
//        notifyItemRemoved(position);
//    }
////    public void restoreItem(Oder_food oder_food, int position){
////        oder_foodList.add(position,oder_food);
////        notifyItemInserted(position);
////    }
}
