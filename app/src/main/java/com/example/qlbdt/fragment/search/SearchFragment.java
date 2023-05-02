package com.example.qlbdt.fragment.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.qlbdt.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Tiến Dũng
 * MVVM + Firebase
 * Thiết kế giao diện
 * */

public class SearchFragment extends Fragment {

    private ProductSearchAdapter productSearchAdapter;
    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;
    private Spinner sp_sort, sp_brand;
    private ArrayList<String> sort, brand,type;
    private  ArrayAdapter adapterSort, adapterBrand,adaptertype;
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
        type(getArguments());

        return view;
    }

    private void type(Bundle arguments) {
        String strType = SearchFragmentArgs.fromBundle(arguments).getTypeProduct();
        type = new ArrayList<>();
        type.add("loại");
        type.add("Máy Tính");
        type.add("Điện Thoại");
        adaptertype = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, type);
        binding.spTypeFragmentSearch.setAdapter(adaptertype);
        binding.spTypeFragmentSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        searchViewModel.typelaptop();
                        break;
                    case 2:
                        searchViewModel.typesmartphone();
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
        sort.add("Sắp xếp theo");
        sort.add("Sắp xếp theo giá tăng dần");
        sort.add("Sắp xếp theo giá giảm dần");
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
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
        binding.rcvSearch.setLayoutManager(linearLayoutManager);
        productSearchAdapter = new ProductSearchAdapter(getContext(), new IRecyclerViewOnClick() {
            @Override
            public void onClickItem(ProductSearch productSearch) {
                SearchFragmentDirections.ActionSearchFragmentToProductDetailFragment action =
                        SearchFragmentDirections.actionSearchFragmentToProductDetailFragment();
                action.setDocumentPath(productSearch.getId());
                Navigation.findNavController(requireView()).navigate(action);
            }
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
                if (!selectedBrand.equals("Hãng")) {
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
        //Nếu muốn search all
        //searchViewModel.fetchAllProducts();
        searchViewModel.getListProductSearchLiveData().observe(getViewLifecycleOwner(), new Observer<List<ProductSearch>>() {
            @Override
            public void onChanged(List<ProductSearch> productList) {
                List<ProductSearch> filteredList = new ArrayList<>();
                for (ProductSearch product : productList) {
                    if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                        filteredList.add(product);
                    }
                }

                productSearchAdapter.setData(filteredList);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();

    }

}
