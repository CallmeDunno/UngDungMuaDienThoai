package com.example.qlbdt.fragment.Account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.qlbdt.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Tiến Dũng
 * */

public class AccountFragment extends Fragment {
    private CircleImageView avatar;
    private TextView name, phoneNum, address, email;
    private Button btn_edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);


        return view;
    }

}