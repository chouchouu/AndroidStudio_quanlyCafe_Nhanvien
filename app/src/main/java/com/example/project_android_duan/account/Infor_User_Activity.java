package com.example.project_android_duan.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.project_android_duan.R;
import com.example.project_android_duan.model.model.model_account.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class Infor_User_Activity extends AppCompatActivity {
    private Account account;
    private  Intent intent;
    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("Account");
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private EditText edt_username, edt_email, edt_phone,edt_password;
    private Button btn_update,btn_updateCancle,btn_rmkpass;
    ImageView img_update;
    private String checkup;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor__user_);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Control();
        Event();
    }

    private void Event() {
        // bắt sự kiện lấy dữ liệu qua từ adapter để update
        intent = getIntent();
        account = (Account) intent.getSerializableExtra("account");

        if (account != null) {
            DownloadImgUpdate(account, img_update);
            edt_username.setText(account.getUsername());
            edt_phone.setText(account.getPhone() + "");
            edt_email.setText(account.getEmail());
            checkup = account.getChucvu();
            Log.d("aaa",account.getChucvu());
            if (checkup.equals("Thu Ngân")){
                RadioButton radio1 = (RadioButton) findViewById(R.id.up_thungan);
                radio1.setChecked(true);

            }else if (checkup.equals("Pha Chế")){
                RadioButton radio2 = (RadioButton) findViewById(R.id.up_phache);
                radio2.setChecked(true);

            }else if (checkup.equals("Quản Lý Kho")){
                RadioButton radio3 =(RadioButton) findViewById(R.id.up_quanly);
                radio3.setChecked(true);
            }
        }
        btn_rmkpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemakePass();
            }
        });
        btn_updateCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // sự kiện update Account
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final String username = edt_username.getText().toString();
                    final String email = edt_email.getText().toString();
                    final String phone = edt_phone.getText().toString();
                    final String password = edt_password.getText().toString();

                    account.setUsername(username);
                    account.setEmail(email);
                    account.setPhone(phone);
                    account.setChucvu(checkup);
                if (username.isEmpty() || email.isEmpty() || phone.isEmpty()){
                    Toast.makeText(Infor_User_Activity.this, "Không Được Để Trống Thông Tin", Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(Infor_User_Activity.this, "Lỗi Sai Mật Khẩu Hoặc Lỗi Người Dùng", Toast.LENGTH_SHORT).show();
                }else {
                    mReference.child(account.getKey()).setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            // Get auth credentials from the user for re-authentication
                            AuthCredential credential = EmailAuthProvider.getCredential(account.getEmail(), password); // Current Login Credentials \\
                            // Prompt the user to re-provide their sign-in credentials
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //Now change your email address \\
                                            //----------------Code for Changing Email Address----------\\
                                            user.updateEmail(email)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(Infor_User_Activity.this, "Update Thành Công", Toast.LENGTH_SHORT).show();
                                                            }else {
                                                                Toast.makeText(Infor_User_Activity.this, "Update Thất Bại", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                            //----------------------------------------------------------\\
                                        }
                                    });
                        }
                    });
                    finish();
                }
                }
        });
    }

    private void RemakePass() {
        final Dialog dialog = new Dialog(Infor_User_Activity.this);
        dialog.setContentView(R.layout.dialog_rmkpass);
        final EditText edt_updatePassold = dialog.findViewById(R.id.edt_password_old);
        final EditText edt_updatePassnew = dialog.findViewById(R.id.edt_password_new);
        final EditText edt_updatePassnewrmk = dialog.findViewById(R.id.edt_passwordnewrmk);
        Button btn_updatePass = dialog.findViewById(R.id.btn_updatePass);
        Button btn_canclePass = dialog.findViewById(R.id.btn_cancelPass);
        btn_canclePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passold = edt_updatePassold.getText().toString();
                String passnew = edt_updatePassnew.getText().toString();
                String passnewrmk = edt_updatePassnewrmk.getText().toString();
                if (passold.isEmpty() || passnew.isEmpty() || passnewrmk.isEmpty()){
                    Toast.makeText(Infor_User_Activity.this, "Yêu Cầu Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updatePassword(passnew)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Infor_User_Activity.this, "Update Thành Công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }else {
                                        Toast.makeText(Infor_User_Activity.this, "Lỗi Người Dùng", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        dialog.show();
    }

    private void DownloadImgUpdate(final Account account, final ImageView img_update){
        Log.d("ImageURL",storageRef.child("imageAccount").getDownloadUrl()+"");
        Log.d("abc",account.getImageURL());
        storageRef.child("imageAccount").child(account.getImageURL()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(Infor_User_Activity.this).load(uri).into(img_update);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void Control() {
        img_update = findViewById(R.id.img_update);
        edt_username = findViewById(R.id.edt_username);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        btn_update = findViewById(R.id.btn_update);
        edt_password = findViewById(R.id.edt_password);
        btn_updateCancle = findViewById(R.id.btn_updateCancle);
        btn_rmkpass = findViewById(R.id.btnrmkpass);
    }
    public void onRadioButtonClicked(View view){
        boolean checkeded = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.up_thungan:
                if (checkeded)
                    checkup = (String) ((RadioButton) view).getText();
                Toast.makeText(this, "Thu Ngân", Toast.LENGTH_SHORT).show();
                break;
            case R.id.up_phache:
                if (checkeded)
                    checkup = (String) ((RadioButton) view).getText();
                Toast.makeText(this, "Pha Chế", Toast.LENGTH_SHORT).show();
                break;
            case R.id.up_quanly:
                if (checkeded)
                    checkup = (String) ((RadioButton) view).getText();
                Toast.makeText(this, "Quản Lý Kho", Toast.LENGTH_SHORT).show();
                break;

        }
    }

}
