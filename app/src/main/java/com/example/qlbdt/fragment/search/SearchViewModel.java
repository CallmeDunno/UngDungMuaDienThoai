package com.example.qlbdt.fragment.search;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlbdt.fragment.home.ProductHome;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<ProductSearch>> listProductSearchLiveData;
    private MutableLiveData<List<String>> brandListLiveData;
    private List<ProductSearch> listProductSearch;
    private String type;

    public SearchViewModel() {
        listProductSearchLiveData = new MutableLiveData<>();
        brandListLiveData = new MutableLiveData<>();
        initData();
    }

    public LiveData<List<String>> getBrandListLiveData() {
        return brandListLiveData;
    }

    public void fetchBrandList() {
        List<String> brandList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Set<String> brandSet = new HashSet<>();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String brand = doc.toObject(ProductSearch.class).getBrand();
                            brandSet.add(brand);
                        }
                        brandList.addAll(brandSet);
                        Collections.sort(brandList);
                        brandList.add(0, "Hãng");
                        brandListLiveData.setValue(brandList);
                    } else {
                        Log.e("SearchViewModel", "Error fetching brand list", task.getException());
                    }
                });
    }

    private void initData() {
        listProductSearch = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e("SearchFragment", "Error getting data", e);
                            return;
                        }

                        listProductSearch.clear();

                        for (QueryDocumentSnapshot doc : snapshots) {
                            String id = doc.getId();
                            String name = doc.toObject(ProductHome.class).getName();
                            String price = doc.toObject(ProductHome.class).getPrice();
                            String image = doc.toObject(ProductHome.class).getImage();
                            String os = doc.toObject(ProductHome.class).getOS();
                            String battery = doc.toObject(ProductHome.class).getBattery();
                            String brand = doc.toObject(ProductHome.class).getBrand();
                            String color = doc.toObject(ProductHome.class).getColor();
                            String cpu = doc.toObject(ProductHome.class).getCpu();
                            String description = doc.toObject(ProductHome.class).getDescription();
                            int quantity = doc.toObject(ProductHome.class).getQuantity();
                            String ram = doc.toObject(ProductHome.class).getRam();
                            String releaseTime = doc.toObject(ProductHome.class).getReleaseTime();
                            String rom = doc.toObject(ProductHome.class).getRom();
                            String type = doc.toObject(ProductHome.class).getType();
                            String weight = doc.toObject(ProductHome.class).getWeight();

                            listProductSearch.add(new ProductSearch(id, name, price, image, os, battery, brand, color, cpu, description, quantity, ram, releaseTime, rom, type, weight));
                        }
                        listProductSearchLiveData.postValue(listProductSearch);
                    }
                });
        listProductSearchLiveData.setValue(listProductSearch);
    }

    public MutableLiveData<List<ProductSearch>> getListProductSearchLiveData() {
        return listProductSearchLiveData;
    }

    public void fetchProductByBrand(String brand) {
        List<ProductSearch> productList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .whereEqualTo("brand", brand)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String name = doc.toObject(ProductSearch.class).getName();
                            String price = doc.toObject(ProductSearch.class).getPrice();
                            String image = doc.toObject(ProductSearch.class).getImage();
                            productList.add(new ProductSearch(name, price, image));
                        }
                        listProductSearchLiveData.postValue(productList);
                    } else {
                        Log.e("SearchViewModel", "Error fetching product list by brand", task.getException());
                    }
                });
    }

    public void fetchAllProducts() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ProductSearch> productList = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String name = doc.toObject(ProductSearch.class).getName();
                            String price = doc.toObject(ProductSearch.class).getPrice();
                            String image = doc.toObject(ProductSearch.class).getImage();
                            productList.add(new ProductSearch(name, price, image));
                        }
                        listProductSearchLiveData.setValue(productList);
                    } else {
                        Log.e("SearchViewModel", "Error fetching all products", task.getException());
                    }
                });
    }


    public void sortProductListByAscendingPrice() {
        List<ProductSearch> sortedList = new ArrayList<>(listProductSearch);
        Collections.sort(sortedList, new Comparator<ProductSearch>() {
            @Override
            public int compare(ProductSearch product1, ProductSearch product2) {
                NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                try {
                    Number number1 = format.parse(product1.getPrice());
                    Number number2 = format.parse(product2.getPrice());
                    return Double.compare(number1.doubleValue(), number2.doubleValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        listProductSearchLiveData.setValue(sortedList);
    }

    public void sortProductListByDescendingPrice() {
        List<ProductSearch> sortedList = new ArrayList<>(listProductSearch);
        Collections.sort(sortedList, new Comparator<ProductSearch>() {
            @Override
            public int compare(ProductSearch product1, ProductSearch product2) {
                NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                try {
                    Number number1 = format.parse(product1.getPrice());
                    Number number2 = format.parse(product2.getPrice());
                    return Double.compare(number2.doubleValue(), number1.doubleValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        listProductSearchLiveData.setValue(sortedList);
    }

    public void setType(String type) {
        this.type = type;
    }
}
