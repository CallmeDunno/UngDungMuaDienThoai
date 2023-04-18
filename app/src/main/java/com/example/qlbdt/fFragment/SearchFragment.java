package com.example.qlbdt.fFragment;

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
    Spinner sp_sort, sp_brand;
    ArrayList<String> sort, brand;
    ArrayAdapter adapterSort, adapterBrand;

    int sortIndex = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        //region Set Data for RecyclerView
        recyclerView = view.findViewById(R.id.rcv_fragment_search);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        smartphones = new ArrayList<>();
        searchAdapter = new SmartphoneSearchAdapter(getActivity(), new IRecyclerViewOnClick() {
            @Override
            public void onClickItemSmartphone(Smartphone smartphone) {

            }
        });
        LoadData();
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

        //region Sort
        sp_sort = view.findViewById(R.id.sp_sort_fragment_search);
        sort = new ArrayList<>();
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
                    case "Sắp xếp theo tên sản phẩm":
                        sortIndex = i;
                        Collections.sort(smpOld, new Smartphone.NameOrder());
                        searchAdapter.setDataChanged(smartphones);
                        break;
                    case "Sắp xếp theo giá tăng dần":
                        sortIndex = i;
                        Collections.sort(smpOld, new Smartphone.PriceOrderAsc());
                        searchAdapter.setDataChanged(smartphones);
                        break;
                    case "Sắp xếp theo giá giảm dần":
                        sortIndex = i;
                        Collections.sort(smpOld, new Smartphone.PriceOrderDesc());
                        searchAdapter.setDataChanged(smartphones);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //endregion

        //region Brand
        sp_brand = view.findViewById(R.id.sp_brand_fragment_search);
        brand = new ArrayList<>();
        brand.add("Hãng");
        //data
        adapterBrand = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, brand);
        sp_brand.setAdapter(adapterBrand);
        sp_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String strBrand = brand.get(i);
                if (!strBrand.equals("Hãng")){
                    List<Smartphone> list = new ArrayList<>();
                    for (Smartphone s : smartphones){
                        if (s.getBrand_name().equals(strBrand)){
                            list.add(s);
                        }
                    }
                    if (sortIndex == 0){
                        Collections.sort(list, new Smartphone.NameOrder());
                    }
                    if (sortIndex == 1){
                        Collections.sort(list, new Smartphone.PriceOrderAsc());
                    }
                    if (sortIndex == 2){
                        Collections.sort(list, new Smartphone.PriceOrderDesc());
                    }
                    searchAdapter.setDataChanged(list);
                } else {
                    searchAdapter.setDataChanged(smartphones);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //endregion

        return view;
    }

    private void LoadData(){
        smartphones.clear();
        //data
        searchAdapter.setData(smartphones);
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadData();
    }
}