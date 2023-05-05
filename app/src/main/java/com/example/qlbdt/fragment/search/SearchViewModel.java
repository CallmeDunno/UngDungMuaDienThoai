package com.example.qlbdt.fragment.search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlbdt.fragment.home.HomeProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
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
                        brandList.add(0, "Brand");
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
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String id = doc.getId();
                                String name = doc.toObject(HomeProduct.class).getName();
                                String price = doc.toObject(HomeProduct.class).getPrice();
                                String image = doc.toObject(HomeProduct.class).getImage();
                                String os = doc.toObject(HomeProduct.class).getOS();
                                String battery = doc.toObject(HomeProduct.class).getBattery();
                                String brand = doc.toObject(HomeProduct.class).getBrand();
                                String color = doc.toObject(HomeProduct.class).getColor();
                                String cpu = doc.toObject(HomeProduct.class).getCpu();
                                String description = doc.toObject(HomeProduct.class).getDescription();
                                String ram = doc.toObject(HomeProduct.class).getRam();
                                String releaseTime = doc.toObject(HomeProduct.class).getReleaseTime();
                                String rom = doc.toObject(HomeProduct.class).getRom();
                                String type = doc.toObject(HomeProduct.class).getType();
                                String weight = doc.toObject(HomeProduct.class).getWeight();

                                listProductSearch.add(new ProductSearch(id, name, price, image, os, battery, brand, color, cpu, description, ram, releaseTime, rom, type, weight));
                            }
                            listProductSearchLiveData.postValue(listProductSearch);
                        }
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
                            String id = doc.getId();
                            String name = doc.toObject(ProductSearch.class).getName();
                            String price = doc.toObject(ProductSearch.class).getPrice();
                            String image = doc.toObject(ProductSearch.class).getImage();
                            productList.add(new ProductSearch(id, name, price, image));
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
                            String id = doc.getId();
                            String name = doc.toObject(ProductSearch.class).getName();
                            String price = doc.toObject(ProductSearch.class).getPrice();
                            String image = doc.toObject(ProductSearch.class).getImage();
                            productList.add(new ProductSearch(id, name, price, image));
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

    public void typeProduct(String type) {
        List<ProductSearch> productList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .whereEqualTo("type", type)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String id = doc.getId();
                            String name = doc.toObject(ProductSearch.class).getName();
                            String price = doc.toObject(ProductSearch.class).getPrice();
                            String image = doc.toObject(ProductSearch.class).getImage();
                            productList.add(new ProductSearch(id, name, price, image));
                        }
                        listProductSearchLiveData.postValue(productList);
                    } else {
                        Log.e("SearchViewModel", "Error fetching product list by brand", task.getException());
                    }
                });
        listProductSearchLiveData.setValue(productList);
    }

    public void typeProduct() {
        List<ProductSearch> productList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String id = doc.getId();
                            String name = doc.toObject(ProductSearch.class).getName();
                            String price = doc.toObject(ProductSearch.class).getPrice();
                            String image = doc.toObject(ProductSearch.class).getImage();
                            productList.add(new ProductSearch(id, name, price, image));
                        }
                        listProductSearchLiveData.postValue(productList);
                    } else {
                        Log.e("SearchViewModel", "Error fetching product list by brand", task.getException());
                    }
                });
    }

}
