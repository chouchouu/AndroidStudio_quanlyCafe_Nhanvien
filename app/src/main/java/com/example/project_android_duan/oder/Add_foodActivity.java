package com.example.project_android_duan.oder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.project_android_duan.model.model.model_oder.Oder_food;
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

public class Add_foodActivity extends AppCompatActivity {

    EditText ten,gia,mota;
    Button them,huy,choose,up;
    ImageView imgfood;

    final int PICK_IMAGE_REQUEST=1;
    Uri mimageUri;
    StorageReference mstorageReference;
    DatabaseReference mdatabaseReference=FirebaseDatabase.getInstance().getReference("Food");;
    public String mimage_url;

    String id=mdatabaseReference.push().getKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        addEvent();
        //ẩn
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mstorageReference = FirebaseStorage.getInstance().getReference("Food");


        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"),PICK_IMAGE_REQUEST);

            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mimageUri != null) {
                    final ProgressDialog progressDialog = new ProgressDialog(Add_foodActivity.this);
                    progressDialog.setTitle("Uploading");
                    progressDialog.show();
                    StorageReference picRef = mstorageReference.child(mimageUri.getLastPathSegment());
                    picRef.putFile(mimageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(Add_foodActivity.this, "UpLoading Thành Công", Toast.LENGTH_SHORT).show();
                            //Lấy link ảnh database
                            if (ten.length() == 0 || mota.length() == 0 || gia.length() == 0) {
                                Toast.makeText(Add_foodActivity.this, "Không bỏ trống thông tin", Toast.LENGTH_SHORT).show();
                            } else {
                                String name = ten.getText().toString();
                                String vieww = mota.getText().toString();
                                int price = Integer.valueOf(gia.getText().toString());
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful()) ;
                                Uri downloadUrl = uriTask.getResult();
                                mimage_url = downloadUrl.toString();

                                Oder_food oder_food = new Oder_food(id, name, vieww, price, mimage_url);
                                //oder_drink.setHinh(image_url);
//                            id = mdatabaseReference.push().getKey();
                                mdatabaseReference.child(id).setValue(oder_food).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Add_foodActivity.this, "Thêm Thành Công", Toast.LENGTH_LONG).show();

                                    }
                                }).addOnCanceledListener(new OnCanceledListener() {
                                    @Override
                                    public void onCanceled() {
                                        Toast.makeText(Add_foodActivity.this, "Thêm Thất Bại", Toast.LENGTH_LONG).show();
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
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==PICK_IMAGE_REQUEST && resultCode ==RESULT_OK &&data!=null&&data.getData()!=null){
            mimageUri=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(Add_foodActivity.this.getContentResolver(),mimageUri);
                imgfood.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }




    private void addEvent() {
        choose=findViewById(R.id.btnchoose_food);
        ten=findViewById(R.id.edt_food);
        mota=findViewById(R.id.edt_mtfood);
        gia=findViewById(R.id.edt_giafood);
        them=findViewById(R.id.btn_addfood);
        huy=findViewById(R.id.btn_cancel);
        imgfood=findViewById(R.id.imgfood);

    }
}
