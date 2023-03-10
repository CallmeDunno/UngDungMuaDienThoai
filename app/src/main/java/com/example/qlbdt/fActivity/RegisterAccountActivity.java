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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlbdt.R;
import com.example.qlbdt.fDatabase.MyDatabase;
import com.example.qlbdt.fEnum.EDesSmartphoneDetail;
import com.example.qlbdt.fObject.User;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class RegisterAccountActivity extends AppCompatActivity {

    EditText edt_name, edt_phone, edt_email, edt_addr;
    TextView tv_w_name, tv_w_phone, tv_w_email, tv_w_addr, tv_w_r_phone, tv_w_r_email;
    ImageView img_avt;
    Button btn_confirm;

    private final int MY_CODE_REQUEST_FOLDER = 100;
    private MyDatabase database;

    private void init() {
        edt_name = findViewById(R.id.edt_username_ca);
        edt_phone = findViewById(R.id.edt_phone_ca);
        edt_email = findViewById(R.id.edt_email_ca);
        edt_addr = findViewById(R.id.edt_address_ca);
        img_avt = findViewById(R.id.img_avt_user_ca);
        btn_confirm = findViewById(R.id.btn_confirm);
        tv_w_name = findViewById(R.id.tv_label_warning_1_ca);
        tv_w_phone = findViewById(R.id.tv_label_warning_2_ca);
        tv_w_email = findViewById(R.id.tv_label_warning_3_ca);
        tv_w_addr = findViewById(R.id.tv_label_warning_4_ca);
        tv_w_r_phone = findViewById(R.id.tv_label_warning_regex_1_ca);
        tv_w_r_email = findViewById(R.id.tv_label_warning_regex_2_ca);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        init();

        database = new MyDatabase(RegisterAccountActivity.this, "MuaBanDienThoai.sqlite", null, 1);

        //region Insert Data
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Apple')");
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Samsung')");
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Oppo')");
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Nokia')");
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Vivo')");
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Xiaomi')");

        database.InsertSmartphone(RegisterAccountActivity.this, "iPhone 13 Pro Max", "31490000", 153,
                R.drawable.apple_iphone_13_pro_max, 1);
        database.InsertSmartphone(RegisterAccountActivity.this, "iPhone 14 Pro Max", "26490000", 98,
                R.drawable.apple_iphone_14_promax, 1);
        database.InsertSmartphone(RegisterAccountActivity.this, "iPhone 11", "11990000", 32,
                R.drawable.iphone_11, 1);
        database.InsertSmartphone(RegisterAccountActivity.this, "iPhone 12", "18490000", 54,
                R.drawable.iphone12_xanh_duong, 1);
        database.InsertSmartphone(RegisterAccountActivity.this, "iPhone 14 Plus", "23090000", 121,
                R.drawable.iphone_14_plus, 1);
        database.InsertSmartphone(RegisterAccountActivity.this, "Nokia G11", "2690000", 31,
                R.drawable.nokia_g11, 4);
        database.InsertSmartphone(RegisterAccountActivity.this, "Oppo Find X5 Pro", "24990000", 146,
                R.drawable.oppo_find_x5_pro, 3);
        database.InsertSmartphone(RegisterAccountActivity.this, "Oppo Reno8 Pro 5G", "17590000", 253
                , R.drawable.oppo_reno8_pro, 3);
        database.InsertSmartphone(RegisterAccountActivity.this, "Samsung Galaxy Z Fold3 5G",
                "32990000", 195, R.drawable.samsung_galaxy_z_fold_3_5g, 2);
        database.InsertSmartphone(RegisterAccountActivity.this, "Samsung Galaxy S20 FE", "9490000",
                31, R.drawable.samsung_galaxy_s20_fe_green, 2);
        database.InsertSmartphone(RegisterAccountActivity.this, "Samsung Galaxy S23 Ultra 5G",
                "41990000", 257, R.drawable.samsung_galaxy_s23_sltra_plus, 2);
        database.InsertSmartphone(RegisterAccountActivity.this, "Samsung Galaxy Z Flip4", "19990000"
                , 184, R.drawable.samsung_galaxy_zflip4_5g, 2);
        database.InsertSmartphone(RegisterAccountActivity.this, "Vivo V25 Pro 5G", "13290000", 43,
                R.drawable.vivo_v25_pro_5g, 5);
        database.InsertSmartphone(RegisterAccountActivity.this, "Vivo Y21", "3290000", 52,
                R.drawable.vivo_y21, 5);
        database.InsertSmartphone(RegisterAccountActivity.this, "Xiaomi 12T Pro 5G", "14090000", 57,
                R.drawable.xiaomi_12t_pro, 6);
        database.InsertSmartphone(RegisterAccountActivity.this, "Xiaomi Redmi Note 11", "4390000", 64,
                R.drawable.xiaomi_redmi_note_11, 6);

        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Apple A15 Bionic', '6GB', '1TB', 'Xanh nhạt', '4352mAh', '240g', '12 tháng', '1')",
                EDesSmartphoneDetail.DES_i13prm.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Apple A16 Bionic', '6GB', '128GB', 'Tím', '3200mAh', '206g', '12 " +
                        "tháng', '2')",
                EDesSmartphoneDetail.DES_i14prm.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Apple A13 Bionic', '4GB', '64GB', 'Trắng', '3110mAh', '164g', '12 " +
                        "tháng', '3')",
                EDesSmartphoneDetail.DES_i11.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Apple A14 Bionic', '4GB', '256GB', 'Xanh dương', '2815mAh', '164g', '12 " +
                        "tháng', '4')",
                EDesSmartphoneDetail.DES_i12.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Apple A15 Bionic', '6GB', '128GB', 'Tím nhạt', '4325mAh', '203g', '12 " +
                        "tháng', '5')",
                EDesSmartphoneDetail.DES_i14p.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Unisoc T606', '4GB', '64GB', 'Xám', '5050mAh', '189g', '12 tháng'," +
                        " '6')",
                EDesSmartphoneDetail.DES_Nokia_G11.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Snapdragon 8 gen 1', '12GB', '256TB', 'Đen', '5000mAh', '218g'," +
                        " '12 tháng', '7')",
                EDesSmartphoneDetail.DES_Oppo_FindX5pro.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'MediaTek Dimensity 8100 Max 8 nhân', '12GB', '256GB', 'Xanh ngọc', '4500mAh', '183g', '12 tháng', '8')",
                EDesSmartphoneDetail.DES_Oppo_Reno8pro.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Snapdragon 888', '12GB', '512GB', 'Xanh rêu', '4400mAh', '271g', '12 tháng', '9')",
                EDesSmartphoneDetail.DES_SS_ZFOLD3.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Snapdragon 865', '8GB', '256GB', 'Xanh lá', '4500mAh', '190g', '12 tháng', '10')",
                EDesSmartphoneDetail.DES_SS_S20FE.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Snapdragon 8 Gen 2 8 nhân', '12GB', '1TB', 'Tím  nhạt', '5000mAh', '223g', '12 tháng', '11')",
                EDesSmartphoneDetail.DES_SS_S23U.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Snapdragon 8+ Gen 1', '8GB', '128GB', 'Tím', '3700mAh', '187g', '12 tháng', '12')",
                EDesSmartphoneDetail.DES_SS_ZFLIP4.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'MediaTek Dimensity 1300', '8GB', '128GB', 'Xanh dương', '4830mAh', '190g', '12 tháng', '13')",
                EDesSmartphoneDetail.DES_VV_V25pro.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'MediaTek Helio P35', '4GB', '64GB', 'Trắng', '5000mAh', '182g', '12 tháng', '14')",
                EDesSmartphoneDetail.DES_VV_Y21.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Snapdragon 8+ Gen 1', '12GB', '256GB', 'Đen', '5000mAh', '205g', '12 tháng', '15')",
                EDesSmartphoneDetail.DES_XM_12Tpro.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " +
                        "'Snapdragon 680', '6GB', '128GB', 'Xanh dương đậm', '5000mAh', '179g', '12 tháng', '16')",
                EDesSmartphoneDetail.DES_XM_N11.getDes()));
        //endregion

        ActivityResultLauncher<Intent> activityPickerResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Uri uri = result.getData().getData();
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                img_avt.setImageBitmap(bitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        img_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(RegisterAccountActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    activityPickerResultLauncher.launch(intent);
                } else {
                    ActivityCompat.requestPermissions(RegisterAccountActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_CODE_REQUEST_FOLDER);
                }

            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisableWarning();
                if (CheckData()){
                    DisableWarning();
                    String name = edt_name.getText().toString().trim();
                    String phone = edt_phone.getText().toString().trim();
                    String email = edt_email.getText().toString().trim();
                    String address = edt_addr.getText().toString().trim();
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) img_avt.getDrawable(); //lấy hình từ imageView
                    //chuyển từ bitmapDrawable -> bitmap
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    //chuyển về mảng byte
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    //Định dạng lại kiểu dữ liệu trước khi xuất
                    //(định dạng ảnh; quality (chất lượng) chạy từ 1 -> 100 càng nhỏ ảnh càng nét, 100 là mặc định; mảng byte)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 1, byteArrayOutputStream);
                    byte[] avatar = byteArrayOutputStream.toByteArray();

                    database.InsertUser(new User(name, phone, email, address, avatar));
                    Toast.makeText(RegisterAccountActivity.this, "Xin chào, " + name, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterAccountActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
    }

    private Boolean CheckData() {
        String regexEmail = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
        String regexPhone = "^0\\d{9}$";
        if (edt_name.getText().toString().trim().length() == 0){
            tv_w_name.setVisibility(View.VISIBLE);
            return false;
        }
        if (edt_phone.getText().toString().trim().length() == 0){
            tv_w_phone.setVisibility(View.VISIBLE);
            tv_w_r_phone.setVisibility(View.INVISIBLE);
            return false;
        }
        if (!Pattern.matches(regexPhone, edt_phone.getText().toString().trim())){  //regex
            tv_w_phone.setVisibility(View.INVISIBLE);
            tv_w_r_phone.setVisibility(View.VISIBLE);
            return false;
        }
        if (edt_email.getText().toString().trim().length() == 0){
            tv_w_email.setVisibility(View.VISIBLE);
            tv_w_r_email.setVisibility(View.INVISIBLE);
            return false;
        }
        if (!Pattern.matches(regexEmail, edt_email.getText().toString().trim())){  //regex
            tv_w_email.setVisibility(View.INVISIBLE);
            tv_w_r_email.setVisibility(View.VISIBLE);
            return false;
        }
        if (edt_addr.getText().toString().trim().length() == 0){
            tv_w_addr.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    private void DisableWarning(){
        tv_w_name.setVisibility(View.INVISIBLE);
        tv_w_phone.setVisibility(View.INVISIBLE);
        tv_w_email.setVisibility(View.INVISIBLE);
        tv_w_addr.setVisibility(View.INVISIBLE);
        tv_w_r_phone.setVisibility(View.INVISIBLE);
        tv_w_r_email.setVisibility(View.INVISIBLE);
    }


}