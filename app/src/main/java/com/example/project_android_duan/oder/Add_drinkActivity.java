package com.example.project_android_duan.oder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project_android_duan.R;
import com.example.project_android_duan.adapter.adapter.adapter_oder.Adapter_Oder_Drink;
import com.example.project_android_duan.model.model.model_oder.Oder_drink;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;

public class Add_drinkActivity extends AppCompatActivity {

    EditText edt_name, edt_gia, edt_mota;
    Button btn_them, btn_huy, btn_choose, btn_up;
    ImageView imgcafe;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    RecyclerView rv_drink;
    Adapter_Oder_Drink drinkadapter;
    List<Oder_drink> drink;
    //tHÊM
    final int PICK_IMAGE_REQUEST=1;
    Uri imageUri;
    StorageReference storageReference;
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Drink");;
    public String image_url;
    String id = databaseReference.push().getKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        addEvent();
        storageReference = FirebaseStorage.getInstance().getReference("Drink");



//        storage = FirebaseStorage.getInstance();
//        storageRef = storage.getReference();

        //Chonn Hình

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"),PICK_IMAGE_REQUEST);

            }
        });
        //uP HÌNH

        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    final ProgressDialog progressDialog = new ProgressDialog(Add_drinkActivity.this);
                    progressDialog.setTitle("Uploading");
                    progressDialog.show();
                    StorageReference picRef = storageReference.child(imageUri.getLastPathSegment());
                    picRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            //Lấy link ảnh database
                            if (edt_name.length() == 0 || edt_mota.length() == 0 || edt_gia.length() == 0) {
                                Toast.makeText(Add_drinkActivity.this, "Không bỏ trống thông tin", Toast.LENGTH_SHORT).show();
                            } else {
                                String ten = edt_name.getText().toString();
                                String mota = edt_mota.getText().toString();
                                int gia = Integer.valueOf(edt_gia.getText().toString());
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful()) ;
                                Uri downloadUrl = uriTask.getResult();
                                image_url = downloadUrl.toString();

                                Oder_drink oder_drink = new Oder_drink(id, ten, mota, gia, image_url);
                                //oder_drink.setHinh(image_url);
//                            id = databaseReference.push().getKey();
                                databaseReference.child(id).setValue(oder_drink).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Add_drinkActivity.this, "Thêm Thành Công", Toast.LENGTH_LONG).show();
                                        finish();

                                    }
                                }).addOnCanceledListener(new OnCanceledListener() {
                                    @Override
                                    public void onCanceled() {
                                        Toast.makeText(Add_drinkActivity.this, "Thêm Thất Bại", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }
        });



        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    //Chụp

//    private void Upfile() {
//
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent,PICK_IMAGE_REQUET);
//        }

    //Chọn

//    private void openChoose() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,PICK_IMAGE_REQUET);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==PICK_IMAGE_REQUEST && resultCode ==RESULT_OK &&data!=null&&data.getData()!=null){
            imageUri=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(Add_drinkActivity.this.getContentResolver(),imageUri);
                imgcafe.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }





    private void addEvent() {
        edt_name = findViewById(R.id.edt_name);
        edt_gia = findViewById(R.id.edt_gia);
        edt_mota = findViewById(R.id.edt_mota);
        btn_them = findViewById(R.id.btn_them);
        btn_huy = findViewById(R.id.btn_huy);
        imgcafe=findViewById(R.id.imgcafe);
        btn_choose=findViewById(R.id.btnchoose);
    }

}
