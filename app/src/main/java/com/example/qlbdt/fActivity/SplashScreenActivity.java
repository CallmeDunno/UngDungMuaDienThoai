package com.example.qlbdt.fActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlbdt.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreenActivity extends AppCompatActivity {

    CircleImageView img;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img = findViewById(R.id.img_circle_splash_screen);
        tv = findViewById(R.id.tv_label_splash_screen);

        Animation animation_img = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.anim_ttc_alpha);
        img.startAnimation(animation_img);
        Animation animation_tv = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.anim_btc_alpha);
        tv.startAnimation(animation_tv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            }
        }, 3300);
    }
}