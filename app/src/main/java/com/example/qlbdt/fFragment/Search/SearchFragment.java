package com.example.qlbdt.fFragment.Search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.FragmentSearchBinding;
import com.example.qlbdt.fAdapter.PhotoAdapter;
import com.example.qlbdt.fAdapter.SmartphoneSearchAdapter;
import com.example.qlbdt.fInterface.IRecyclerViewOnClick;
import com.example.qlbdt.fObject.Photo;
import com.example.qlbdt.fObject.Smartphone;
import com.example.qlbdt.fObject.User2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.transform.sax.TemplatesHandler;

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
    private SearchView sv_search;


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
        sv_search = view.findViewById(R.id.sv_fragment_search);
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProducts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProducts(newText);
                return true;
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
            adapterBrand.add("Tất cả các hãng");
        });
        sp_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedBrand = (String) adapterBrand.getItem(i);
                if (!selectedBrand.equals("Hãng") && !selectedBrand.equals("Tất cả các hãng")) {
                    searchViewModel.fetchProductByBrand(selectedBrand);
                } else {
                    searchViewModel.fetchAllProducts();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void searchProducts(String keyword) {
        // Fetch all products from the ViewModel
        searchViewModel.fetchAllProducts();

        // Observe changes to the list of products in the ViewModel
        searchViewModel.getListProductSearchLiveData().observe(getViewLifecycleOwner(), new Observer<List<ProductSearch>>() {
            @Override
            public void onChanged(List<ProductSearch> productList) {
                // Filter the list of products based on the search keyword
                List<ProductSearch> filteredList = new ArrayList<>();
                for (ProductSearch product : productList) {
                    if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                        filteredList.add(product);
                    }
                }

                // Update the RecyclerView with the filtered list of products
                producSearchAdapter.setData(filteredList);
            }
        });
    }



}
