package com.example.qlbdt.fActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qlbdt.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditActivity extends AppCompatActivity {
    private CircleImageView avatar_edit;
    private EditText et_name_edit, et_phoneNum_edit, et_address_edit, et_email_edit;
    private ImageView back_account, done_edit;

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

        et_name_edit.setText(ten_edit);
        et_phoneNum_edit.setText(sdt_edit);
        et_address_edit.setText(dc_edit);
        et_email_edit.setText(email_edit);

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

                String editquery = String.format("UPDATE Person SET name = '%s', phone = '%s', address = '%s', email = '%s' WHERE person_id = 1",
                        ten_done, sdt_done, dc_done, email_done);

                HomeActivity.database.QueryDatabase(editquery);
                Toast.makeText(EditActivity.this, "Done", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}