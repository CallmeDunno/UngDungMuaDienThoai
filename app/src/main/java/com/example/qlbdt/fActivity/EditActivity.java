package com.example.qlbdt.fActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlbdt.R;
import com.example.qlbdt.fDatabase.MyDatabase;
import com.example.qlbdt.fObject.User;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditActivity extends AppCompatActivity {
    ImageView avatar_edit, back_account, done_edit;
    EditText et_name_edit, et_phoneNum_edit, et_address_edit, et_email_edit;
    TextView tv_change_avt;
    MyDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        avatar_edit = findViewById(R.id.avatar_edit_QA);
        et_name_edit = findViewById(R.id.name_edit_QA);
        et_phoneNum_edit = findViewById(R.id.phone_edit_QA);
        et_address_edit = findViewById(R.id.address_edit_QA);
        et_email_edit = findViewById(R.id.email_edit_QA);
        back_account = findViewById(R.id.back_QA);
        done_edit = findViewById(R.id.done_edit_QA);
        tv_change_avt = findViewById(R.id.change_photo_QA);

        database = new MyDatabase(EditActivity.this, "MuaBanDienThoai.sqlite", null, 1);

        String accountquery = "SELECT name, phone, address, email, avatar FROM Person";
        Cursor c = HomeActivity.database.SelectData(accountquery);
        c.moveToFirst();

        String ten = c.getString(0);
        String sdt = c.getString(1);
        String dc = c.getString(2);
        String mail = c.getString(3);
        byte[] image = c.getBlob(4);

        et_name_edit.setText(ten);
        et_phoneNum_edit.setText(sdt);
        et_address_edit.setText(dc);
        et_email_edit.setText(mail);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        avatar_edit.setImageBitmap(bitmap);

        ActivityResultLauncher<Intent> activityPickerResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Uri uri = result.getData().getData();
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                avatar_edit.setImageBitmap(bitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        tv_change_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(EditActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    activityPickerResultLauncher.launch(intent);
                } else {
                    ActivityCompat.requestPermissions(EditActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RegisterAccountActivity.MY_CODE_REQUEST_FOLDER);
                }

            }
        });

        back_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        done_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten_done = et_name_edit.getText().toString().trim();
                String sdt_done = et_phoneNum_edit.getText().toString().trim();
                String dc_done = et_address_edit.getText().toString().trim();
                String email_done = et_email_edit.getText().toString().trim();

                BitmapDrawable bitmapDrawable = (BitmapDrawable) avatar_edit.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 1, byteArrayOutputStream);
                byte[] image = byteArrayOutputStream.toByteArray();

                database.UpdateUser(new User(ten_done, sdt_done, dc_done, email_done, image));
                onBackPressed();
            }
        });

        et_name_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = s.toString().trim();
                if (name.isEmpty()) {
                    et_name_edit.setError("Họ và tên không được để trống!");
                } else {
                    et_name_edit.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_phoneNum_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNumber = s.toString().trim();
                if (phoneNumber.isEmpty()) {
                    et_phoneNum_edit.setError("Số điện thoại không được để trống!");
                } else if (phoneNumber.length() < 10 || phoneNumber.length() > 10) {
                    et_phoneNum_edit.setError("Số điện thoại phải đủ 10 số!");
                } else {
                    et_phoneNum_edit.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_address_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = s.toString().trim();
                if (name.isEmpty()) {
                    et_address_edit.setError("Địa chỉ không được để trống!");
                } else {
                    et_address_edit.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_email_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString().trim();
                if (email.isEmpty()) {
                    et_email_edit.setError("Email không được để trống!");
                }else if (!isValidEmail(email)) {
                    et_email_edit.setError("Email không đúng định dạng!");
                } else {
                    et_email_edit.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    private boolean isValidEmail(String email) {
        // Tạo một Regular Expression để so khớp chuỗi với mẫu email hợp lệ
        String emailRegex = "^[A-Za-z0-9+_.-]+@gmail\\.com$";
        // Kiểm tra xem chuỗi có khớp với mẫu email hợp lệ hay không
        return email.matches(emailRegex);
    }
}