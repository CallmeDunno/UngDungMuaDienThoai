package com.example.qlbdt.fragment.history;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class HistoryViewModel extends ViewModel {
    private MutableLiveData<List<History>> lstHistoryLiveData;
    private List<History> lstHistory;
    private String userID;

    public HistoryViewModel() {
        lstHistoryLiveData = new MutableLiveData<>();
    }

    private void initData(String userID) {
        lstHistory = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Histories")
                .whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()){
                            String uID = document.toObject(History.class).getUserID();
                            String pID = document.toObject(History.class).getProductID();
                            String price = document.toObject(History.class).getPrice();
                            String image = document.toObject(History.class).getImage();
                            String name = document.toObject(History.class).getName();
                            String brand = document.toObject(History.class).getBrand();
                            int quantity = document.toObject(History.class).getQuantity();
                            String timeToBuy = document.toObject(History.class).getTimeToBuy();
                            lstHistory.add(new History(uID, pID, price, image, name, brand, quantity, timeToBuy));
                        }
                        lstHistoryLiveData.postValue(lstHistory);
                    }
                });
        lstHistoryLiveData.setValue(lstHistory);
    }

    public MutableLiveData<List<History>> getLstHistoryLiveData() {
        return lstHistoryLiveData;
    }

    public void setUserID(String userID) {
        this.userID = userID;
        initData(userID);
    }
}
