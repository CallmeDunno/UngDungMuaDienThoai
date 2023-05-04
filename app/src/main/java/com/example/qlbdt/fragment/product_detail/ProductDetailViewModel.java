package com.example.qlbdt.fragment.product_detail;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlbdt.fragment.basket.Basket;
import com.example.qlbdt.fragment.basket.BasketDatabase;
import com.example.qlbdt.fragment.history.History;
import com.example.qlbdt.fragment.home.HomeProduct;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class ProductDetailViewModel extends ViewModel {
    private final MutableLiveData<HomeProduct> productHomeMutableLiveData;
    private String documentPath;
    private HomeProduct homeProduct;

    public ProductDetailViewModel() {
        productHomeMutableLiveData = new MutableLiveData<>();
    }

    private void initData() {
        homeProduct = new HomeProduct();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .document(documentPath)
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

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
        initData();
    }

    public void pushHistory(String userID, HomeProduct homeProduct, int quantity) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String datetimeCurr = simpleDateFormat.format(now.getTime());

        History history = new History(userID,
                homeProduct.getId(),
                homeProduct.getPrice(),
                homeProduct.getImage(),
                homeProduct.getName(),
                homeProduct.getBrand(),
                quantity,
                datetimeCurr);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Histories")
                .add(history)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.d("ProductDetail", "Push histories: Done");
                    } else {
                        Log.e("ProductDetail", "Push histories fail");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("ProductDetail", e.getMessage());
                });
    }

    public void addToCart(Context context, String userID, HomeProduct homeProduct, int quantity){
        Basket basket = new Basket(homeProduct.getId(),
                userID,
                homeProduct.getName(),
                homeProduct.getPrice(),
                quantity,
                homeProduct.getImage(),
                homeProduct.getBrand());
        BasketDatabase.getInstance(context).basketDao().InsertBasket(basket);
    }
}
