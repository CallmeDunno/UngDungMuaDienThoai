package com.example.qlbdt.fFragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.qlbdt.R;
import com.example.qlbdt.fActivity.EditActivity;
import com.example.qlbdt.fActivity.HomeActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    private CircleImageView avatar;
    private TextView name, phoneNum, address, email;
    private Button btn_edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        avatar = view.findViewById(R.id.avatar_QA);
        name = view.findViewById(R.id.name_QA);
        phoneNum = view.findViewById(R.id.phone_QA);
        address = view.findViewById(R.id.address_QA);
        email = view.findViewById(R.id.email_QA);
        btn_edit = view.findViewById(R.id.btn_edit_QA);

        String accountquery = "SELECT name, phone, address, email, avatar FROM Person";
        Cursor c = HomeActivity.database.SelectData(accountquery);
        c.moveToFirst();

        String ten = c.getString(0);
        String sdt = c.getString(1);
        String dc = c.getString(2);
        String mail = c.getString(3);

        name.setText(ten);
        phoneNum.setText(sdt);
        address.setText(dc);
        email.setText(mail);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                Bundle bundle = new Bundle();
               // bundle.putByteArray();
                bundle.putString("name",ten);
                bundle.putString("phone", sdt);
                bundle.putString("address", dc);
                bundle.putString("email", mail);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }


}