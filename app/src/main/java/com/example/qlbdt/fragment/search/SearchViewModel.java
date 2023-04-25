package com.example.qlbdt.fragment.search;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
                .whereEqualTo("type", "Smartphone")
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
                .whereEqualTo("type", "Smartphone")
                .limit(5)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e("SearchFragment", "Error getting data", e);
                            return;
                        }

                        listProductSearch.clear();

                        for (QueryDocumentSnapshot doc : snapshots) {
                            String name = doc.toObject(ProductSearch.class).getName();
                            String price = doc.toObject(ProductSearch.class).getPrice();
                            String image = doc.toObject(ProductSearch.class).getImage();

                            listProductSearch.add(new ProductSearch(name, price, image));
                        }
                        Collections.shuffle(listProductSearch);
                        listProductSearchLiveData.postValue(listProductSearch);
                    }
                });
    }
   /* private void initData() {
        listProductSearch = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .whereEqualTo("type", "Smartphone")
                .limit(5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){

                                String name = doc.toObject(ProductSearch.class).getName();
                                String price = doc.toObject(ProductSearch.class).getPrice();
                                String image = doc.toObject(ProductSearch.class).getImage();

                                listProductSearch.add(new ProductSearch(name, price, image));
                            }
                            Collections.shuffle(listProductSearch);
                            listProductSearchLiveData.postValue(listProductSearch);

                        } else {
                            Log.e("SearchFragment", "not data");
                        }
                    }
                });

        listProductSearchLiveData.setValue(listProductSearch);

    }*/


    public MutableLiveData<List<ProductSearch>> getListProductSearchLiveData() {
        return listProductSearchLiveData;
    }

    public void fetchProductByBrand(String brand) {
        List<ProductSearch> productList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .whereEqualTo("type", "Smartphone")
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

}
