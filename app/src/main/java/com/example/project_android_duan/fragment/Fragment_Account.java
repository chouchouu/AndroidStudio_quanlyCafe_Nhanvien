package com.example.project_android_duan.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android_duan.R;
import com.example.project_android_duan.account.Add_Account_Activity;
import com.example.project_android_duan.adapter.adapter.adapter_account.Adapter_Account;
import com.example.project_android_duan.model.model.model_account.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Account extends Fragment {
    EditText txt_search;
    private static ArrayList<Account> arrayList = new ArrayList<>();
    DatabaseReference mrefe = FirebaseDatabase.getInstance().getReference();
    FloatingActionButton fab;
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private static Adapter_Account adapter_account;
    private RecyclerView recy_account;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        txt_search = view.findViewById(R.id.txt_search);
        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });
        recy_account = view.findViewById(R.id.recy_account);
        final String id = firebaseAuth.getUid();
        recy_account.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recy_account.setLayoutManager(linearLayoutManager);
        recy_account.setItemAnimator(new DefaultItemAnimator());
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                DataSnapshot dataacoount = dataSnapshot.child("Account");
                for (DataSnapshot valueclass : dataacoount.getChildren()) {
                    Account account = valueclass.getValue(Account.class);
                    arrayList.add(account);
                    adapter_account = new Adapter_Account(arrayList,getContext());
                    recy_account.setAdapter(adapter_account);
                    adapter_account.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        mrefe.addValueEventListener(eventListener);

//        fab = view.findViewById(R.id.fab);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), Add_Account_Activity.class));
//                Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;

    }
    public static void dialogBuider(final Account account, final Context context){
     final AlertDialog.Builder builder = new AlertDialog.Builder(context);
     builder.setTitle("Thông Báo");
     builder.setMessage("Bạn Có Muốn Xóa Không ?");
     builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
             DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
            myref.child("Account").child(firebaseAuth.getUid()).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                            adapter_account.notifyDataSetChanged();
                                        }
                                    }
                                });

                    }
                }
            });

         }
     });
     builder.show();
    }
    private void filter(String string){
        ArrayList<Account> filterlist = new ArrayList<>();
        for (Account hoaDon:arrayList){
            if (hoaDon.getUsername().toLowerCase().contains(string.toLowerCase())){
                filterlist.add(hoaDon);
            }
        }
        adapter_account.filterlist(filterlist);
    }


}
