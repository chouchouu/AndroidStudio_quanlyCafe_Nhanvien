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
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project_android_duan.R;
import com.example.project_android_duan.adapter.adapter.adapter_account.Adapter_Account;
import com.example.project_android_duan.adapter.adapter.adapter_oder.Adapter_Oder_Drink;
import com.example.project_android_duan.model.model.model_account.Account;
import com.example.project_android_duan.model.model.model_oder.Oder_drink;
import com.example.project_android_duan.model.model.model_oder.Oder_food;
import com.example.project_android_duan.oder.Add_drinkActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Fragment_oder_drink extends Fragment {
    RecyclerView rv_drink;
    Adapter_Oder_Drink drinkadapter;
    List<Oder_drink> drink;
    String id="";
    DatabaseReference myRe = FirebaseDatabase.getInstance().getReference();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);

        drink = new ArrayList<>();
        rv_drink = view.findViewById(R.id.recyclerview_oder_drink);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_drink.setLayoutManager(linearLayoutManager);
        rv_drink.setHasFixedSize(true);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot data = dataSnapshot.child("Drink");
                drink.clear();
                for (DataSnapshot valueclass : data.getChildren()) {
                    Oder_drink oder_drink = valueclass.getValue(Oder_drink.class);

                    drinkadapter = new Adapter_Oder_Drink(drink,getContext());
                    drink.add(oder_drink);
                    rv_drink.setAdapter(drinkadapter);
                    drinkadapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        myRe.addValueEventListener(eventListener);



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rv_drink);

    }

    Oder_drink deletedrink = null;
    List<Oder_drink> archihve = new ArrayList<>();
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, final int direction) {
            final int position = viewHolder.getAdapterPosition();


            switch (direction) {
                case ItemTouchHelper.LEFT:
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Bạn Có Muốn Xóa Không ?");
                    builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Oder_drink s = drink.get(position);
                            DatabaseReference myRef =FirebaseDatabase.getInstance().getReference();
                            Query apples =myRef.child("Drink").orderByChild("name").equalTo(s.getName());
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
                            deletedrink = drink.get(position);
                            drink.remove(position);
                            drinkadapter.notifyItemRemoved(position);
                        }
                    });
                    builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            drink.add(position, deletedrink);
                            drinkadapter.notifyItemInserted(position);
                        }
                    });
                    builder.show();
                    Snackbar.make(rv_drink, "Khôi phục", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    drink.add(position, deletedrink);
                                    drinkadapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;
                case ItemTouchHelper.RIGHT:
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








//Nước
//        final ArrayList<Oder_drink> oderdrinkArrayList = new ArrayList<>();


//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                DataSnapshot dataoder = dataSnapshot.child("Oder");
//                for (DataSnapshot valueclass : dataoder.getChildren()){
//                    Oder_drink oder_drink=valueclass.getValue(Oder_drink.class);
//                    oderdrinkArrayList.add(oder_drink);
//                    adapter_oder_drink[0] = new Adapter_Oder_Drink(oderdrinkArrayList,getContext());
//                    rv_drink.setAdapter(adapter_oder_drink[0]);
//                    adapter_oder_drink[0].notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        myRef.addValueEventListener(eventListener);

//        ItemTouchHelper.SimpleCallback simpleCallback1 = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT,Fragment_oder_drink.this);
//        new ItemTouchHelper(simpleCallback1).attachToRecyclerView(rv_drink);




//    @Override
//    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
//        if (viewHolder instanceof Adapter_Oder_Drink.ViewHolder) {
//            adapter_oder_drink.removeItem(viewHolder.getAdapterPosition());
//
//        }
//    }

