package com.example.qlbdt.fragment.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<HomeProduct>> listHomeProductLiveData;
    private List<HomeProduct> listHomeProduct;

    public HomeViewModel() {
        listHomeProductLiveData = new MutableLiveData<>();

        initData();
    }

    public void initData() {
        listHomeProduct = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .get()
                .addOnCompleteListener(task -> {
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

                            listHomeProduct.add(new HomeProduct(id, name, price, image, os, battery, brand, color, cpu, description, ram, releaseTime, rom, type, weight));
                        }
                        Collections.shuffle(listHomeProduct);
                        listHomeProductLiveData.postValue(listHomeProduct);
                    }
                });
        listHomeProductLiveData.setValue(listHomeProduct);
    }

    public MutableLiveData<List<HomeProduct>> getListHomeProductLiveData() {
        return listHomeProductLiveData;
    }
}
