package com.example.qlbdt.fActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlbdt.R;
import com.example.qlbdt.fDatabase.MyDatabase;
import com.example.qlbdt.fFragment.SearchFragment;
import com.example.qlbdt.fOther.Notification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SmartphoneDetailActivity extends AppCompatActivity {

    ImageView img_back, img_avt;
    TextView tv_name_smp_detail, tv_price_smp_detail, tv_des_smp_detail, tv_brand_name_smp_detail,
            tv_cpu_smp_detail, tv_ram_smp_detail, tv_rom_smp_detail, tv_color_smp_detail, tv_battery_smp_detail, tv_weight_smp_detail,
            tv_wp_smp_detail, tv_label_smp_detail;
    Button btn_buy_smp_detail;

    private MyDatabase database;

    private void init() {
        img_back = findViewById(R.id.img_back_smp_detail);
        img_avt = findViewById(R.id.img_avt_smp_detail);
        tv_name_smp_detail = findViewById(R.id.tv_name_smp_detail);
        tv_price_smp_detail = findViewById(R.id.tv_price_smp_detail);
        tv_des_smp_detail = findViewById(R.id.tv_des_smp_detail);
        tv_brand_name_smp_detail = findViewById(R.id.tv_brand_name_smp_detail);
        tv_cpu_smp_detail = findViewById(R.id.tv_cpu_smp_detail);
        tv_ram_smp_detail = findViewById(R.id.tv_ram_smp_detail);
        tv_rom_smp_detail = findViewById(R.id.tv_rom_smp_detail);
        tv_color_smp_detail = findViewById(R.id.tv_color_smp_detail);
        tv_battery_smp_detail = findViewById(R.id.tv_battery_smp_detail);
        tv_weight_smp_detail = findViewById(R.id.tv_weight_smp_detail);
        btn_buy_smp_detail = findViewById(R.id.btn_buy_smp_detail);
        tv_wp_smp_detail = findViewById(R.id.tv_wp_smp_detail);
        tv_label_smp_detail = findViewById(R.id.tv_label_smp_detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartphone_detail);

        database = new MyDatabase(SmartphoneDetailActivity.this, "MuaBanDienThoai.sqlite", null, 1);

        init();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("NameSmartphone");
        SetContentSmartphoneDetail(name);

        btn_buy_smp_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog(name);
            }
        });
    }

    private void SetContentSmartphoneDetail(String name) {
        tv_label_smp_detail.setText(name);
        tv_name_smp_detail.setText(name);
        String querySmartphoneDetail = String.format("SELECT Brand.name, Smartphone.price, Smartphone.avatar, SmartphoneDetail.description, " +
                "SmartphoneDetail.CPU, SmartphoneDetail.RAM, SmartphoneDetail.ROM, SmartphoneDetail.color, SmartphoneDetail.battery, " +
                "SmartphoneDetail.weight, SmartphoneDetail.warrantyPeriod " +
                "FROM Smartphone JOIN SmartphoneDetail ON Smartphone.smartphone_id = SmartphoneDetail.smartphone_id " +
                "JOIN Brand ON Brand.brand_id = Smartphone.brand_id " +
                "WHERE Smartphone.name = '%s'", name);
        Cursor c = database.SelectData(querySmartphoneDetail);
        c.moveToFirst();

        tv_brand_name_smp_detail.setText(c.getString(0));
        tv_price_smp_detail.setText("Giá: " + c.getString(1) + " VND");

        byte[] image = c.getBlob(2);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        img_avt.setImageBitmap(bitmap);

        tv_des_smp_detail.setText("Mô tả sản phẩm: " + c.getString(3));
        tv_cpu_smp_detail.setText(c.getString(4));
        tv_ram_smp_detail.setText(c.getString(5));
        tv_rom_smp_detail.setText(c.getString(6));
        tv_color_smp_detail.setText(c.getString(7));
        tv_battery_smp_detail.setText(c.getString(8));
        tv_weight_smp_detail.setText(c.getString(9));
        tv_wp_smp_detail.setText(c.getString(10));
    }

    private void ShowDialog(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn thực sự muốn mua sản phẩm này?");
        builder.setPositiveButton("Tôi đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SendNoti(name);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String orderTime = dateFormat.format(calendar.getTime());

                String queryGetIDsmp = String.format("SELECT SmartphoneDetail.smartphone_detail_id, Smartphone.quantity " +
                        "FROM SmartphoneDetail JOIN Smartphone ON SmartphoneDetail.smartphone_id = Smartphone.smartphone_id " +
                        "WHERE name = '%s'", name);
                Cursor cursorIDsmp = database.SelectData(queryGetIDsmp);
                cursorIDsmp.moveToFirst();
                int id_smp = cursorIDsmp.getInt(0);
                int quantity_smp = cursorIDsmp.getInt(1);

                String insertHistory = String.format("INSERT INTO History VALUES(NULL, '%s', %d, 1)", orderTime, id_smp);
                database.QueryDatabase(insertHistory);

                quantity_smp -= 1;
                String updateQuantitySmartphone = String.format("UPDATE Smartphone SET quantity = %d WHERE name = '%s'", quantity_smp, name);
                database.QueryDatabase(updateQuantitySmartphone);
            }
        });
        builder.setNeutralButton("Không, tôi không muốn mua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private void SendNoti(String name) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo_app);
        String content = "Chúc mừng bạn đã mua thành công điện thoại " + name;
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, Notification.CHANNEL_ID)
                .setContentTitle("Thông báo")
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setSmallIcon(R.drawable.ic_smartphone)
                .setLargeIcon(bitmap);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(GetTimeNoti(), notification.build());

    }

    //gửi noti nhiều lần
    private int GetTimeNoti() {
        return (int) new Date().getTime();
    }
}