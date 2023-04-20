package com.example.qlbdt.fFragment.Home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<ProductHome>> listProductHomeLiveData;

    private List<ProductHome> listProductHome;

    public HomeViewModel() {
        listProductHomeLiveData = new MutableLiveData<>();

        initData();
    }

    private void initData() {
        listProductHome = new ArrayList<>();

        String url = "https://firebasestorage.googleapis.com/v0/b/productmanager-ttcm-01.appspot.com/o/apple_iphone_13_pro_max.png?alt=media&token=609e647f-f001-4531-88d3-3e35f8c23b62";
        listProductHome.add(new ProductHome("iPhone 13 Pro Max", "23.323.000", url));
        listProductHome.add(new ProductHome("Samsung Galaxy S23 Ultra", "23.323.000", url));
        listProductHome.add(new ProductHome("a", "23.323.000", url));
        listProductHome.add(new ProductHome("a", "23.323.000", url));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .whereEqualTo("type", "Smartphone")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){

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

                                listProductHome.add(new ProductHome(name, price, image, os, battery, brand, color, cpu, description, quantity, ram, releaseTime, rom,  type, weight));
                            }
                        } else {
                            Log.e("HomeFragment", "not data");
                        }
                    }
                });

        listProductHomeLiveData.setValue(listProductHome);
    }

    public MutableLiveData<List<ProductHome>> getListProductHomeLiveData() {
        return listProductHomeLiveData;
    }
}
