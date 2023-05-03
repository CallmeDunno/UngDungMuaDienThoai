package com.example.qlbdt.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;
    public static SharedPreferences userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initAction();

    }

    private void initAction() {
        new Handler().postDelayed(() -> {
            String currentUser = userDatabase.getString("currentUser", null);
            if (currentUser != null){
                Toast.makeText(SplashScreenActivity.this, "Welcome, " + currentUser, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
            } else {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            }
            finish();
        }, 3300);
    }

    private void initView() {
        Animation animation_img = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.anim_ttc_alpha);
        binding.imgCircleSplashScreen.startAnimation(animation_img);
        Animation animation_tv = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.anim_btc_alpha);
        binding.tvLabelSplashScreen.startAnimation(animation_tv);

        userDatabase = getSharedPreferences("userDatabase", Context.MODE_PRIVATE);
    }
}