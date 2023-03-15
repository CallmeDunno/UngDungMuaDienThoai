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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlbdt.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditActivity extends AppCompatActivity {
    ImageView avatar_edit, back_account, done_edit;
    EditText et_name_edit, et_phoneNum_edit, et_address_edit, et_email_edit;
    TextView tv_change_avt;

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

        Intent intent = getIntent();
        if(intent == null){
            return;
        }
        Bundle bundle = intent.getExtras();
        if(bundle == null){
            return;
        }
        String ten_edit = bundle.getString("name");
        String sdt_edit = bundle.getString("phone");
        String dc_edit = bundle.getString("address");
        String email_edit = bundle.getString("email");
        byte[] avt_edit = bundle.getByteArray("avatar");

        et_name_edit.setText(ten_edit);
        et_phoneNum_edit.setText(sdt_edit);
        et_address_edit.setText(dc_edit);
        et_email_edit.setText(email_edit);
        Bitmap bitmap = BitmapFactory.decodeByteArray(avt_edit, 0, avt_edit.length);
        avatar_edit.setImageBitmap(bitmap);

        ActivityResultLauncher<Intent> activityPickerResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
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
                if(ContextCompat.checkSelfPermission(EditActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
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

                String editquery = String.format("UPDATE Person SET name = '%s', phone = '%s', address = '%s', email = '%s', avatar = '"+image+"' WHERE person_id = 1",
                        ten_done, sdt_done, dc_done, email_done);

                HomeActivity.database.QueryDatabase(editquery);
                Toast.makeText(EditActivity.this, "Done", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

    }
}