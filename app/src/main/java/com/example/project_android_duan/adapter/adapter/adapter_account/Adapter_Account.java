package com.example.project_android_duan.adapter.adapter.adapter_account;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android_duan.R;
import com.example.project_android_duan.account.Infor_User_Activity;
import com.example.project_android_duan.fragment.Fragment_Account;
import com.example.project_android_duan.model.model.model_account.Account;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Account extends RecyclerView.Adapter<Adapter_Account.ViewHolder> {
    private List<Account> accountList;
    private Context context;
    private StorageReference storage = FirebaseStorage.getInstance().getReference();

    public Adapter_Account(List<Account> accountList, Context context){
        this.accountList = accountList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_account,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Account account = accountList.get(position);
        holder.tv_accountUser.setText(account.getUsername());
        holder.tv_accountImport.setText(account.getChucvu());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Infor_User_Activity.class);
                intent.putExtra("account",account);
                context.startActivity(intent);
            }
        });
//        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Fragment_Account.dialogBuider(account,context);
//                return false;
//            }
//        });
        DownloadImg(account,holder.img_account);
    }

    private void DownloadImg(final Account account, final CircleImageView img_account){
        Log.d("ImageURL",storage.child("imageAccount").getDownloadUrl()+"");

        storage.child("imageAccount").child(account.getImageURL()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(context).load(uri).into(img_account);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }
    public  void filterlist(ArrayList<Account> filterlist){
        accountList = filterlist;
        notifyDataSetChanged();
    }

     public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_account;
        TextView tv_accountUser , tv_accountImport;
        CardView cardView;
         public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_account = itemView.findViewById(R.id.img_account);
            tv_accountUser = itemView.findViewById(R.id.tv_accountUser);
            tv_accountImport = itemView.findViewById(R.id.tv_accountImport);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
