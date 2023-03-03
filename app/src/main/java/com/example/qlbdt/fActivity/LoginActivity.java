package com.example.qlbdt.fActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlbdt.R;


public class LoginActivity extends AppCompatActivity {

    EditText edt_username, edt_password;
    Button btn_login, btn_create_acc;
    TextView tv_rs_pass;

    private void init(){
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_create_acc = findViewById(R.id.btn_create_account);
        tv_rs_pass = findViewById(R.id.tv_label_rs_pass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = edt_username.getText().toString().trim();
                String strPassword = edt_password.getText().toString().trim();
                if (strUsername.equals("admin") && strPassword.equals("admin")){
                    Toast.makeText(LoginActivity.this, "ADMIN", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                } else {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        });

        btn_create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterAccountActivity.class));
            }
        });
    }
}