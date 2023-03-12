package com.example.qlbdt.fFragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.fActivity.HomeActivity;
import com.example.qlbdt.fActivity.SmartphoneDetailActivity;
import com.example.qlbdt.fAdapter.SmartphoneSearchAdapter;
import com.example.qlbdt.fInterface.IRecyclerViewOnClick;
import com.example.qlbdt.fObject.Smartphone;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Smartphone> smartphones;
    SmartphoneSearchAdapter searchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.rcv_fragment_search);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        smartphones = new ArrayList<>();
        smartphones.clear();
        Cursor c = HomeActivity.database.SelectData("SELECT * FROM Smartphone");
        while (c.moveToNext()){
            String n = c.getString(1);
            String p = c.getString(2);
            int q = c.getInt(3);
            byte[] a = c.getBlob(4);
            Smartphone smartphone = new Smartphone(n, p, q, a);
            smartphones.add(smartphone);
        }

        searchAdapter = new SmartphoneSearchAdapter(getContext(), smartphones, new IRecyclerViewOnClick() {
            @Override
            public void onClickItemSmartphone(Smartphone smartphone) {
                Intent intent = new Intent(getContext(), SmartphoneDetailActivity.class);
                intent.putExtra("NameSmartphone", smartphone.getName());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(searchAdapter);

        return view;
    }
}