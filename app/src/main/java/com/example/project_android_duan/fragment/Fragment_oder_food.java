package com.example.project_android_duan.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project_android_duan.R;
import com.example.project_android_duan.adapter.adapter.adapter_oder.Adapter_Oder_Drink;
import com.example.project_android_duan.adapter.adapter.adapter_oder.Adapter_Oder_Food;
import com.example.project_android_duan.model.model.model_oder.Oder_drink;
import com.example.project_android_duan.model.model.model_oder.Oder_food;
import com.example.project_android_duan.oder.Add_foodActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Fragment_oder_food extends Fragment {
    Adapter_Oder_Food foodadapter;
    RecyclerView rv_food;
    List<Oder_food> food;
    DatabaseReference Ref = FirebaseDatabase.getInstance().getReference();

    public Fragment_oder_food(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_food,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        food = new ArrayList<>();

        rv_food = view.findViewById(R.id.recyclerview_oder_food);


        //Layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_food.setLayoutManager(linearLayoutManager);
        rv_food.setHasFixedSize(true);

        //Thêm trên recyclerview
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot data= dataSnapshot.child("Food");
                food.clear();
                for (DataSnapshot valueclass : data.getChildren()) {
                    Oder_food food_model = valueclass.getValue(Oder_food.class);
                    food.add(food_model);
                    foodadapter = new Adapter_Oder_Food(food,getContext());
                    rv_food.setAdapter(foodadapter);
                    foodadapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Ref.addValueEventListener(valueEventListener);



//Xóa recycleview
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rv_food);

    }
    Oder_food deletefood = null;
    List<Oder_food> archihve=new ArrayList<>();
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, final int direction) {
            final int position = viewHolder.getAdapterPosition();

            switch (direction){
                case ItemTouchHelper.LEFT:
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Bạn Có Muốn Xóa Không ?");
                    builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Oder_food s = food.get(position);
                            DatabaseReference myRe =FirebaseDatabase.getInstance().getReference();
                            Query apples =myRe.child("Food").orderByChild("name").equalTo(s.getName());
                            apples.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                        snapshot.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            deletefood= food.get(position);
                            food.remove(position);
                            foodadapter.notifyItemRemoved(position);
                        }
                    });
                    builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            food.add(position,deletefood);
                            foodadapter.notifyItemInserted(position);
                        }
                    });
                    builder.show();
                    Snackbar.make(rv_food,"Khôi phục",Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    food.add(position,deletefood);
                                    foodadapter.notifyItemInserted(position);

                                }
                            }).show();
                    break;
                case  ItemTouchHelper.RIGHT:

                    Snackbar.make(rv_food,"Khôi Phục",Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {

                                }
                            }).show();

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorText))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };



}
