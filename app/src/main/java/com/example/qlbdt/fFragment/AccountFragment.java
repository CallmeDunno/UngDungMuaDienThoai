package com.example.qlbdt.fFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.qlbdt.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    private CircleImageView avatar;
    private TextView name, phoneNum, address, email;
    private Button btn_edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        avatar = view.findViewById(R.id.avatar_QA);
        name = view.findViewById(R.id.name_QA);
        phoneNum = view.findViewById(R.id.phone_QA);
        address = view.findViewById(R.id.address_QA);
        email = view.findViewById(R.id.email_QA);
        btn_edit = view.findViewById(R.id.btn_edit_QA);

        setData();

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    private void setData(){

    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
}