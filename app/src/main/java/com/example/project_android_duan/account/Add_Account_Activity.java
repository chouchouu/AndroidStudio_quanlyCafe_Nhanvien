package com.example.project_android_duan.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.project_android_duan.R;
import com.example.project_android_duan.fragment.Fragment_Account;
import com.example.project_android_duan.model.model.model_account.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

public class Add_Account_Activity extends AppCompatActivity {

    private StorageReference storageRef;
    private FirebaseStorage storage;
    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static final int PICK_IMAGE_REQUET = 1;
    static final int REQUEST_IMAGE_CAMERA = 123;
    private ImageView img_avatar;
    private EditText txt_username, txt_email, txt_phone, txt_password,remakePass;
    private Button btn_adduser, btn_canceluser;
    private String checin;
    private Uri Imageuri;
    String id = mReference.push().getKey();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xnxx);
        Controls();
        Events();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        String id = mReference.push().getKey();
        mReference = FirebaseDatabase.getInstance().getReference("Account");

    }
    // Bắt Sự kiện thêm hình , tạo tài khoản Auth và thêm người dùng lên firebase
    private void Events() {
        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        btn_canceluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = txt_username.getText().toString();
                final String email = txt_email.getText().toString();
                final String phone = txt_phone.getText().toString();
                final String password = txt_password.getText().toString();
                final String remakepass = remakePass.getText().toString();
                if(Imageuri == null || user.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || checin.isEmpty()){
                    Toast.makeText(Add_Account_Activity.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                }else if(remakepass.isEmpty()) {
                    Toast.makeText(Add_Account_Activity.this, "Yêu Cầu Xác Nhận Mật Khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    if (Imageuri != null) {
                        final ProgressDialog progressDialog = new ProgressDialog(Add_Account_Activity.this);
                        progressDialog.setTitle("Uploading...");
                        progressDialog.show();
                        final StorageReference ref = storageRef.child("imageAccount").child("image" + UUID.randomUUID().toString());
                        ref.putFile(Imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Account account = new Account(firebaseAuth.getUid(),ref.getName(), user, email, phone, checin);

                                            mReference.child(firebaseAuth.getUid()).setValue(account).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(Add_Account_Activity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                        }else {
                                            Toast.makeText(Add_Account_Activity.this, "Thất Bại", Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                    }
                                });



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(Add_Account_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded" + (int) progress + "%");
                            }
                        });

                    }
                }
            }
        });

    }



    private void showDialog() {
        final Dialog dialog = new Dialog(Add_Account_Activity.this);
        dialog.setContentView(R.layout.custom_dialogimage);
        Button openfile = dialog.findViewById(R.id.openfile);
        // Bắt Sự Kiện openfile
        openfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfleChosse();
                dialog.dismiss();
            }
        });
        Button camera = dialog.findViewById(R.id.camera);
        // bắt sự kiện chụp hình
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakeCamera();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
        private void TakeCamera() {
        Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA);
        }
    }

    private void openfleChosse() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUET && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Imageuri = data.getData();
            Picasso.with(this).load(Imageuri).into(img_avatar);
        }
        if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == RESULT_OK && data !=null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img_avatar.setImageBitmap(imageBitmap);
        }

    }

//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }




        private void Controls() {
            txt_password = findViewById(R.id.txt_password1);
            img_avatar = findViewById(R.id.img_avatar1);
            txt_username = findViewById(R.id.txt_username1);
            txt_email = findViewById(R.id.txt_email1);
            txt_phone = findViewById(R.id.txt_phone1);
            btn_adduser = findViewById(R.id.btn_adduser1);
            btn_canceluser = findViewById(R.id.btn_cancleuser1);
            remakePass = findViewById(R.id.remakePass);
        }


    public void onRadioButtonClicked (View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.thungan1:
                if (checked)
                    checin = (String) ((RadioButton) view).getText();
                break;
            case R.id.phache1:
                if (checked)
                    checin = (String) ((RadioButton) view).getText();
                     Toast.makeText(this, "Pha Chế", Toast.LENGTH_SHORT).show();
                break;
            case R.id.quanlykho1:
                if (checked)
                    checin = (String) ((RadioButton) view).getText();
                     Toast.makeText(this, "Quản Lý Kho", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

