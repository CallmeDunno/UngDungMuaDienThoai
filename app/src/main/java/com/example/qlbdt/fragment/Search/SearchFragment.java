package com.example.qlbdt.fragment.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.FragmentSearchBinding;
import com.example.qlbdt.fInterface.IRecyclerViewOnClick;
import com.example.qlbdt.object.Smartphone;

import java.util.ArrayList;
import java.util.List;

/**
 * Tiến Dũng
 * MVVM + Firebase
 * Thiết kế giao diện
 * */

public class SearchFragment extends Fragment {

    private ProducSearchAdapter producSearchAdapter;
    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;

    private Spinner sp_sort, sp_brand;
    private ArrayList<String> sort, brand;
    private  ArrayAdapter adapterSort, adapterBrand;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        View view =binding.getRoot();

        initUI();
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.getListProductSearchLiveData().observe(getActivity(), new Observer<List<ProductSearch>>() {
            @Override
            public void onChanged(List<ProductSearch> productSearches) {
                producSearchAdapter.setData(productSearches);
            }
        });

        sp_sort = view.findViewById(R.id.sp_sort_fragment_search);
        sort = new ArrayList<>();
        sort.add("Sắp xếp theo tên sản phẩm");
        sort.add("Sắp xếp theo giá tăng dần");
        sort.add("Sắp xếp theo giá giảm dần");
        adapterSort = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sort);
        sp_sort.setAdapter(adapterSort);

        sp_brand = view.findViewById(R.id.sp_brand_fragment_search);
        brand = new ArrayList<>();
        brand.add("Hãng");
        adapterBrand = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, brand);
        sp_brand.setAdapter(adapterBrand);

        return view;
    }



    private void initUI() {
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
        binding.rcvSearch.setLayoutManager(linearLayoutManager);
        producSearchAdapter = new ProducSearchAdapter(getActivity(), new IRecyclerViewOnClick() {
            @Override
            public void onClickItemSmartphone(Smartphone smartphone) {

            }
        });

        binding.rcvSearch.setAdapter(producSearchAdapter);
    }



}
