package com.example.qlbdt.fActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.qlbdt.R;

public class RegisterAccountActivity extends AppCompatActivity {

    ImageView img_arrow_left;

    private void init(){
        img_arrow_left = findViewById(R.id.img_arrow_left);

        img_arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterAccountActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        init();
    }
}