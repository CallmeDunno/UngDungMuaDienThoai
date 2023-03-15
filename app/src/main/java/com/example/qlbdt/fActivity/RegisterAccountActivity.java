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

    public static final int MY_CODE_REQUEST_FOLDER = 100;
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
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Samsung')"); //2
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Oppo')"); //3
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Nokia')");
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Vivo')"); //5
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Xiaomi')");
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Realme')"); //7
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'TCL')"); //8
        database.QueryDatabase("INSERT INTO Brand VALUES(NULL, 'Itel')"); //9

        database.InsertSmartphone(RegisterAccountActivity.this, "iPhone 13 Pro Max", "31.490.000", 153,
                R.drawable.apple_iphone_13_pro_max, 1);
        database.InsertSmartphone(RegisterAccountActivity.this, "iPhone 14 Pro Max", "26.490.000", 98,
                R.drawable.apple_iphone_14_promax, 1);
        database.InsertSmartphone(RegisterAccountActivity.this, "iPhone 11", "11.990.000", 32,
                R.drawable.iphone_11, 1);
        database.InsertSmartphone(RegisterAccountActivity.this, "iPhone 12", "18.490.000", 54,
                R.drawable.iphone12_xanh_duong, 1);
        database.InsertSmartphone(RegisterAccountActivity.this, "iPhone 14 Plus", "23.090.000", 121,
                R.drawable.iphone_14_plus, 1);
        database.InsertSmartphone(RegisterAccountActivity.this, "Nokia G11", "2.690.000", 31,
                R.drawable.nokia_g11, 4);
        database.InsertSmartphone(RegisterAccountActivity.this, "Oppo Find X5 Pro", "24.990.000", 146,
                R.drawable.oppo_find_x5_pro, 3);
        database.InsertSmartphone(RegisterAccountActivity.this, "Oppo Reno8 Pro 5G", "17.590.000", 253
                , R.drawable.oppo_reno8_pro, 3);
        database.InsertSmartphone(RegisterAccountActivity.this, "Samsung Galaxy Z Fold3 5G",
                "32.990.000", 195, R.drawable.samsung_galaxy_z_fold_3_5g, 2);
        database.InsertSmartphone(RegisterAccountActivity.this, "Samsung Galaxy S20 FE", "9.490.000",
                31, R.drawable.samsung_galaxy_s20_fe_green, 2);
        database.InsertSmartphone(RegisterAccountActivity.this, "Samsung Galaxy S23 Ultra 5G",
                "41.990.000", 257, R.drawable.samsung_galaxy_s23_sltra_plus, 2);
        database.InsertSmartphone(RegisterAccountActivity.this, "Samsung Galaxy Z Flip4", "19.990.000"
                , 184, R.drawable.samsung_galaxy_zflip4_5g, 2);
        database.InsertSmartphone(RegisterAccountActivity.this, "Vivo V25 Pro 5G", "13.290.000", 43,
                R.drawable.vivo_v25_pro_5g, 5);
        database.InsertSmartphone(RegisterAccountActivity.this, "Vivo Y21", "3.290.000", 52,
                R.drawable.vivo_y21, 5);
        database.InsertSmartphone(RegisterAccountActivity.this, "Xiaomi 12T Pro 5G", "14.090.000", 57,
                R.drawable.xiaomi_12t_pro, 6);
        database.InsertSmartphone(RegisterAccountActivity.this, "Xiaomi Redmi Note 11", "4.390.000", 64, R.drawable.xiaomi_redmi_note_11, 6);

        database.InsertSmartphone(RegisterAccountActivity.this, "OPPO A55", "3.690.000", 54, R.drawable.oppo_a55, 3);
        database.InsertSmartphone(RegisterAccountActivity.this, "OPPO A77s", "6.290.000", 376, R.drawable.oppo_a77s, 3);
        database.InsertSmartphone(RegisterAccountActivity.this, "OPPO A95", "5.490.000", 43, R.drawable.oppo_a95, 3);
        database.InsertSmartphone(RegisterAccountActivity.this, "OPPO A16", "2.790.000", 53, R.drawable.oppo_a16, 3);
        database.InsertSmartphone(RegisterAccountActivity.this, "Vivo Y02", "2.790.000", 51, R.drawable.vivo_y02, 5);
        database.InsertSmartphone(RegisterAccountActivity.this, "Vivo Y33s", "5.140.000", 43, R.drawable.vivo_y33s, 5);
        database.InsertSmartphone(RegisterAccountActivity.this, "Realme 10", "6.790.000", 64, R.drawable.realme_10, 7);
        database.InsertSmartphone(RegisterAccountActivity.this, "Realme C35", "3.590.000", 76, R.drawable.realme_c35, 7);
        database.InsertSmartphone(RegisterAccountActivity.this, "Realme C33", "3.090.000", 76, R.drawable.realme_c33, 7);
        database.InsertSmartphone(RegisterAccountActivity.this, "Samsung Galaxy A14 5G", "5.190.000", 65, R.drawable.samsung_galaxy_a14, 2);
        database.InsertSmartphone(RegisterAccountActivity.this, "Samsung Galaxy A03s", "2.990.000", 17, R.drawable.samsung_galaxy_a03s, 2);
        database.InsertSmartphone(RegisterAccountActivity.this, "TCL 30+", "3.190.000", 87, R.drawable.tcl_30_plus, 8);
        database.InsertSmartphone(RegisterAccountActivity.this, "TCL 30 SE", "2.540.000", 85, R.drawable.tcl_30_se, 8);
        database.InsertSmartphone(RegisterAccountActivity.this, "Itel L6502", "1.890.000", 32, R.drawable.itel_i6502, 9);
        database.InsertSmartphone(RegisterAccountActivity.this, "Itel L6006", "1.690.000", 64, R.drawable.itel_i6006, 9);

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
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'MediaTek Helio G35', '4GB', '64GB', 'Xanh lá', '5000mAh', '192g', '12 tháng', '17')", EDesSmartphoneDetail.DES_Oppo_A55.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'Snapdragon 680', '8GB', '128GB', 'Đen', '5000mAh', '192g', '12 tháng', '18')", EDesSmartphoneDetail.DES_Oppo_A77s.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'Snapdragon 662', '8GB', '128GB', 'Bạc', '5000mAh', '192g', '12 tháng', '19')", EDesSmartphoneDetail.DES_Oppo_A95.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'MediaTek Helio G35', '3GB', '32GB', 'Bạc', '5000mAh', '192g', '12 tháng', '20')", EDesSmartphoneDetail.DES_Oppo_A16.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'MediaTek MT6762', '2GB', '32GB', 'Xanh tím', '5000mAh', '192g', '12 tháng', '21')", EDesSmartphoneDetail.DES_VV_Y02.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'MediaTek Helio G80', '8GB', '128GB', 'Xanh hồng', '5000mAh', '192g', '12 tháng', '22')", EDesSmartphoneDetail.DES_VV_Y33s.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'MediaTek Helio G99', '8GB', '256GB', 'Trắng', '5000mAh', '192g', '12 tháng', '23')", EDesSmartphoneDetail.DES_RM_10.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'Unisoc T616', '4GB', '64GB', 'Xanh ngọc', '5000mAh', '192g', '12 tháng', '24')", EDesSmartphoneDetail.DES_RM_C35.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'Unisoc Tiger T612', '3GB', '32GB', 'Xanh', '5000mAh', '192g', '12 tháng', '25')", EDesSmartphoneDetail.DES_RM_C33.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'MediaTek Dimensity 700', '4GB', '128GB', 'Đen', '5000mAh', '192g', '12 tháng', '26')", EDesSmartphoneDetail.DES_SS_A14.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'MediaTek MT6765', '4GB', '64GB', 'Đen', '5000mAh', '192g', '12 tháng', '27')", EDesSmartphoneDetail.DES_SS_A03s.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'MediaTek Helio G37', '4GB', '128GB', 'Xanh dương', '5000mAh', '192g', '12 tháng', '28')", EDesSmartphoneDetail.DES_TCL_30p.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'MediaTek Helio G25', '4GB', '128GB', 'Xám', '5000mAh', '192g', '12 tháng', '29')", EDesSmartphoneDetail.DES_TCL_30se.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'Spreadtrum SC9832E', '3GB', '32GB', 'Đen', '5000mAh', '192g', '12 tháng', '30')", EDesSmartphoneDetail.DES_ITEL_L6502.getDes()));
        database.QueryDatabase(String.format("INSERT INTO SmartphoneDetail VALUES(NULL, '%s', " + "'Spreadtrum SC9832E', '2GB', '32GB', 'Tím', '5000mAh', '192g', '12 tháng', '31')", EDesSmartphoneDetail.DES_ITEL_L6006.getDes()));
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