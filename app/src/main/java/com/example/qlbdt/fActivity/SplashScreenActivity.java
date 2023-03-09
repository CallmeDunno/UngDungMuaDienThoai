package com.example.qlbdt.fActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlbdt.R;
import com.example.qlbdt.fDatabase.MyDatabase;
import com.example.qlbdt.fEnum.EDesSmartphoneDetail;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreenActivity extends AppCompatActivity {

    CircleImageView img;
    TextView tv;
    private MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img = findViewById(R.id.img_circle_splash_screen);
        tv = findViewById(R.id.tv_label_splash_screen);

        database = new MyDatabase(SplashScreenActivity.this, "MuaBanDienThoai.sqlite", null, 1);

        Animation animation_img = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.anim_ttc_alpha);
        img.startAnimation(animation_img);
        Animation animation_tv = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.anim_btc_alpha);
        tv.startAnimation(animation_tv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String query = "SELECT * FROM Person";
                Cursor c = database.SelectData(query);

                if (c != null && c.moveToFirst()) {
                    Toast.makeText(SplashScreenActivity.this, "Xin chào, " + c.getString(1), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, RegisterAccountActivity.class));
                }
                finish();
            }
        }, 3300);
    }
}