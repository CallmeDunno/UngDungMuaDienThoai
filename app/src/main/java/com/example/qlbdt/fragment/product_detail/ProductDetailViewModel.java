package com.example.qlbdt.fragment.product_detail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlbdt.fragment.home.HomeProduct;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProductDetailViewModel extends ViewModel {
    private final MutableLiveData<HomeProduct> productHomeMutableLiveData;
    private String path;
    private HomeProduct homeProduct;

    public ProductDetailViewModel() {
        productHomeMutableLiveData = new MutableLiveData<>();
    }

    private void initData() {
        homeProduct = new HomeProduct();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .document(path)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            homeProduct.setId(document.getId());
                            homeProduct.setName(Objects.requireNonNull(document.toObject(HomeProduct.class)).getName());
                            homeProduct.setPrice(Objects.requireNonNull(document.toObject(HomeProduct.class)).getPrice());
                            homeProduct.setImage(Objects.requireNonNull(document.toObject(HomeProduct.class)).getImage());
                            homeProduct.setOS(Objects.requireNonNull(document.toObject(HomeProduct.class)).getOS());
                            homeProduct.setBattery(Objects.requireNonNull(document.toObject(HomeProduct.class)).getBattery());
                            homeProduct.setBrand(Objects.requireNonNull(document.toObject(HomeProduct.class)).getBrand());
                            homeProduct.setColor(Objects.requireNonNull(document.toObject(HomeProduct.class)).getColor());
                            homeProduct.setCpu(Objects.requireNonNull(document.toObject(HomeProduct.class)).getCpu());
                            homeProduct.setDescription(Objects.requireNonNull(document.toObject(HomeProduct.class)).getDescription());
                            homeProduct.setRam(Objects.requireNonNull(document.toObject(HomeProduct.class)).getRam());
                            homeProduct.setReleaseTime(Objects.requireNonNull(document.toObject(HomeProduct.class)).getReleaseTime());
                            homeProduct.setRom(Objects.requireNonNull(document.toObject(HomeProduct.class)).getRom());
                            homeProduct.setWeight(Objects.requireNonNull(document.toObject(HomeProduct.class)).getWeight());

                            productHomeMutableLiveData.postValue(homeProduct);
                        } else {
                            Log.e("Detail", "document null");
                        }
                    } else {
                        Log.e("Detail", "task null");
                    }
                });
        productHomeMutableLiveData.setValue(homeProduct);
    }

    public MutableLiveData<HomeProduct> getProductHomeMutableLiveData() {
        return productHomeMutableLiveData;
    }

    public void setPath(String path) {
        this.path = path;
        initData();
    }
}
