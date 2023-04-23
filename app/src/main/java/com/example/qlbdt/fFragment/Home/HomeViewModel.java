package com.example.qlbdt.fFragment.Home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<ProductHome>> listProductHomeLiveData;
    private List<ProductHome> listProductHome;

    public HomeViewModel() {
        listProductHomeLiveData = new MutableLiveData<>();

        initData();
    }

    public void initData() {
        listProductHome = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {

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

                            listProductHome.add(new ProductHome(name, price, image, os, battery, brand, color, cpu, description, quantity, ram, releaseTime, rom, type, weight));
                        }
                        Collections.shuffle(listProductHome);
                        listProductHomeLiveData.postValue(listProductHome);
                    } else {
                        showDialogError(); //TODO: Cần biến context để khởi tạo dialog hoặc Toast => Chưa biết cách giải quyết =))
                    }
                });
        listProductHomeLiveData.setValue(listProductHome);
    }

    /** Method showDialogError
     * Hiển thị hộp thoại thông báo trong trường hợp lấy dữ liệu từ firebase bị lỗi
     * Author: Dunno
     * */
    private void showDialogError() {

    }

    public MutableLiveData<List<ProductHome>> getListProductHomeLiveData() {
        return listProductHomeLiveData;
    }
}
