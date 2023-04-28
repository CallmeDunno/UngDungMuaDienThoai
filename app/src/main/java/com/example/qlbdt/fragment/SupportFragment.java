package com.example.qlbdt.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.qlbdt.R;
import com.example.qlbdt.adapter.SupportAdapter;
import com.example.qlbdt.object.Support;

import java.util.ArrayList;

public class SupportFragment extends Fragment {
    ListView listViewSupport;
    ArrayList<Support> lstSupport;
    SupportAdapter supportAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        return view;
    }
}