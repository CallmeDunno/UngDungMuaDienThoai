package com.example.qlbdt.fFragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.SearchView;
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
import java.util.Collections;
import java.util.List;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Smartphone> smartphones;
    SmartphoneSearchAdapter searchAdapter;

    SearchView searchView;

    Spinner sp_sort;
    ArrayList<String> sort;
    ArrayAdapter adapterSort;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        //region Set Data for RecyclerView
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

        searchAdapter = new SmartphoneSearchAdapter(getContext(), new IRecyclerViewOnClick() {
            @Override
            public void onClickItemSmartphone(Smartphone smartphone) {
                Intent intent = new Intent(getContext(), SmartphoneDetailActivity.class);
                intent.putExtra("NameSmartphone", smartphone.getName());
                startActivity(intent);
            }
        });
        searchAdapter.setData(smartphones);
        recyclerView.setAdapter(searchAdapter);
        //endregion

        //region Search + Filter
        searchView = view.findViewById(R.id.sv_fragment_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        //endregion

        sp_sort = view.findViewById(R.id.sp_sort_fragment_search);
        sort = new ArrayList<>();
        sort.add("Sắp xếp theo mặc định");
        sort.add("Sắp xếp theo tên sản phẩm");
        sort.add("Sắp xếp theo giá tăng dần");
        sort.add("Sắp xếp theo giá giảm dần");
        adapterSort = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sort);
        sp_sort.setAdapter(adapterSort);
        sp_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Smartphone> smpOld = smartphones;
                switch (sort.get(i)){
                    case "Sắp xếp theo mặc định":
                        searchAdapter.setDataChanged(smpOld);
                        break;
                    case "Sắp xếp theo tên sản phẩm":
                        Collections.sort(smartphones, new Smartphone.NameOrder());
                        searchAdapter.setDataChanged(smartphones);
                        break;
                    case "Sắp xếp theo giá tăng dần":
                        Collections.sort(smartphones, new Smartphone.PriceOrderAsc());
                        searchAdapter.setDataChanged(smartphones);
                        break;
                    case "Sắp xếp theo giá giảm dần":
                        Collections.sort(smartphones, new Smartphone.PriceOrderDesc());
                        searchAdapter.setDataChanged(smartphones);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }
}