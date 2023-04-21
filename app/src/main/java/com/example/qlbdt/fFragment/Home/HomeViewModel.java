package com.example.qlbdt.fFragment.Home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<ProductHome>> listProductHomeLiveData;
    private List<ProductHome> listProductHome;
    public HomeViewModel() {
        listProductHomeLiveData = new MutableLiveData<>();

        initData();
    }

    private void initData() {
        listProductHome = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .whereEqualTo("type", "Smartphone")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot doc : task.getResult()){

                            String name = doc.toObject(ProductHome.class).getName();
                            String price = doc.toObject(ProductHome.class).getPrice();
                            String image = doc.toObject(ProductHome.class).getImage();
                            //region Tạm thời không dùng đến
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

                                listProductHome.add(new ProductHome(name, price, image, os, battery, brand, color, cpu, description, quantity, ram, releaseTime, rom,  type, weight));
                            //endregion
//                            listProductHome.add(new ProductHome(name, price, image));
                        }
//                        Collections.shuffle(listProductHome);
                        listProductHomeLiveData.postValue(listProductHome);
                    } else {
                        Log.e("HomeFragment", "not data");
                    }
                });
        listProductHomeLiveData.setValue(listProductHome);
    }

    public MutableLiveData<List<ProductHome>> getListProductHomeLiveData() {
        return listProductHomeLiveData;
    }
}
