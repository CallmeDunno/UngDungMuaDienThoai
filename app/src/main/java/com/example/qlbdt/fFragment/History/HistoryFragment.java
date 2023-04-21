package com.example.qlbdt.fFragment.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.qlbdt.R;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Sơn
 * MVVM + Firebase
 * Thiết kế giao diện
 *
 * */

public class HistoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }

}