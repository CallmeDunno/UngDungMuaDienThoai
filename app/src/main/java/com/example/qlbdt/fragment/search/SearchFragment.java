package com.example.qlbdt.fragment.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.qlbdt.databinding.FragmentSearchBinding;
import com.example.qlbdt.fragment.home.TypeProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Tiến Dũng
 * MVVM + Firebase
 * Thiết kế giao diện
 */

public class SearchFragment extends Fragment {

    private ProductSearchAdapter productSearchAdapter;
    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;
    private ArrayList<String> sort, brand, type;
    private ArrayAdapter adapterSort, adapterBrand, adaptertype;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.fetchBrandList();

        searchViewModel.getListProductSearchLiveData().observe(getActivity(), new Observer<List<ProductSearch>>() {
            @Override
            public void onChanged(List<ProductSearch> productSearches) {
                productSearchAdapter.setData(productSearches);
            }
        });

        binding.svFragmentSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        sort();
        Brand();
        type();
    }

    private void type() {
        type = new ArrayList<>();
        type.add("Type");
        type.add(TypeProduct.Laptop.name());
        type.add(TypeProduct.Smartphone.name());
        adaptertype = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, type);
        binding.spTypeFragmentSearch.setAdapter(adaptertype);

        binding.spTypeFragmentSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        searchViewModel.fetchAllProducts();
                        break;
                    case 1:
                        searchViewModel.typeProduct(TypeProduct.Laptop.name());
                        break;
                    case 2:
                        searchViewModel.typeProduct(TypeProduct.Smartphone.name());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void sort() {
        sort = new ArrayList<>();
        sort.add("Sort");
        sort.add("Sort by price (asc)");
        sort.add("Sort bu price (desc)");
        adapterSort = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sort);
        binding.spSortFragmentSearch.setAdapter(adapterSort);
        binding.spSortFragmentSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        searchViewModel.sortProductListByAscendingPrice();
                        break;
                    case 2:
                        searchViewModel.sortProductListByDescendingPrice();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rcvSearch.setLayoutManager(linearLayoutManager);
        productSearchAdapter = new ProductSearchAdapter(getContext(), productSearch -> {
            SearchFragmentDirections.ActionSearchFragmentToProductDetailFragment action =
                    SearchFragmentDirections.actionSearchFragmentToProductDetailFragment();
            action.setDocumentPath(productSearch.getId());
            Navigation.findNavController(requireView()).navigate(action);
        });

        binding.rcvSearch.setAdapter(productSearchAdapter);
    }

    private void Brand() {
        adapterBrand = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        binding.spBrandFragmentSearch.setAdapter(adapterBrand);
        searchViewModel.getBrandListLiveData().observe(getViewLifecycleOwner(), brandList -> {
            adapterBrand.clear();
            adapterBrand.addAll(brandList);

        });
        binding.spBrandFragmentSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedBrand = (String) adapterBrand.getItem(i);
                if (!selectedBrand.equals("Brand")) {
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
        searchViewModel.getListProductSearchLiveData().observe(getViewLifecycleOwner(), productList -> {
            List<ProductSearch> filteredList = new ArrayList<>();
            for (ProductSearch product : productList) {
                if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredList.add(product);
                }
            }

            productSearchAdapter.setData(filteredList);
        });
    }

}
