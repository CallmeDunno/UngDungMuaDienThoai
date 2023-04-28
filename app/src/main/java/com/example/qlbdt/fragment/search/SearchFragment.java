package com.example.qlbdt.fragment.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
    private TextView et_search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        View view =binding.getRoot();

        initUI();
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.fetchBrandList();

        searchViewModel.getListProductSearchLiveData().observe(getActivity(), new Observer<List<ProductSearch>>() {
            @Override
            public void onChanged(List<ProductSearch> productSearches) {
                producSearchAdapter.setData(productSearches);
            }
        });
        et_search = view.findViewById(R.id.et_search_fragment_search);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    searchProducts(et_search.getText().toString());
                    return true;
                }
                return false;
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchProducts(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_brand = view.findViewById(R.id.sp_brand_fragment_search);
        Brand();

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
    private void Brand() {
        adapterBrand = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        sp_brand.setAdapter(adapterBrand);
        searchViewModel.getBrandListLiveData().observe(getViewLifecycleOwner(), brandList -> {
            adapterBrand.clear();
            adapterBrand.addAll(brandList);
        });
        sp_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedBrand = (String) adapterBrand.getItem(i);
                if (!selectedBrand.equals("Hãng")) {
                    searchViewModel.fetchProductByBrand(selectedBrand);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void searchProducts(String keyword) {
        // Lấy danh sách sản phẩm từ ViewModel
        List<ProductSearch> productList = searchViewModel.getListProductSearchLiveData().getValue();

        // Lọc danh sách sản phẩm theo từ khóa tìm kiếm
        List<ProductSearch> filteredList = new ArrayList<>();
        for (ProductSearch product : productList) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(product);
            }
        }

        // Cập nhật danh sách sản phẩm trong Adapter
        producSearchAdapter.setData(filteredList);
    }


}
