package com.example.qlbdt.fragment.ProductDetail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlbdt.fragment.Home.ProductHome;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProductDetailViewModel extends ViewModel {
    private final MutableLiveData<ProductHome> productHomeMutableLiveData;
    private String path;
    private ProductHome productHome;

    public ProductDetailViewModel() {
        productHomeMutableLiveData = new MutableLiveData<>();
    }

    private void initData() {
        productHome = new ProductHome();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products").document(path)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if (document != null){
                            productHome.setId(document.getId());
                            productHome.setName(Objects.requireNonNull(document.toObject(ProductHome.class)).getName());
                            productHome.setPrice(Objects.requireNonNull(document.toObject(ProductHome.class)).getPrice());
                            productHome.setImage(Objects.requireNonNull(document.toObject(ProductHome.class)).getImage());
                            productHome.setOS(Objects.requireNonNull(document.toObject(ProductHome.class)).getOS());
                            productHome.setBattery(Objects.requireNonNull(document.toObject(ProductHome.class)).getBattery());
                            productHome.setBrand(Objects.requireNonNull(document.toObject(ProductHome.class)).getBrand());
                            productHome.setColor(Objects.requireNonNull(document.toObject(ProductHome.class)).getColor());
                            productHome.setCpu(Objects.requireNonNull(document.toObject(ProductHome.class)).getCpu());
                            productHome.setDescription(Objects.requireNonNull(document.toObject(ProductHome.class)).getDescription());
                            productHome.setRam(Objects.requireNonNull(document.toObject(ProductHome.class)).getRam());
                            productHome.setReleaseTime(Objects.requireNonNull(document.toObject(ProductHome.class)).getReleaseTime());
                            productHome.setRom(Objects.requireNonNull(document.toObject(ProductHome.class)).getRom());
                            productHome.setWeight(Objects.requireNonNull(document.toObject(ProductHome.class)).getWeight());

                            productHomeMutableLiveData.postValue(productHome);
                        } else {
                            Log.e("Detail", "document null");
                        }
                    } else {
                        Log.e("Detail", "task null");
                    }
                });
        productHomeMutableLiveData.setValue(productHome);
    }

    public MutableLiveData<ProductHome> getProductHomeMutableLiveData(){
        return productHomeMutableLiveData;
    }

    public void setPath(String path) {
        this.path = path;
        initData();
    }
}
