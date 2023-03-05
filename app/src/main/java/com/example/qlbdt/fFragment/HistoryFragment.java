package com.example.qlbdt.fFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.qlbdt.R;

public class HistoryFragment extends Fragment {

    TextView textView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        textView = view.findViewById(R.id.tvhistory);

        Toast.makeText(getActivity(), "Hehe", Toast.LENGTH_SHORT).show();
        
        return view;
    }
}