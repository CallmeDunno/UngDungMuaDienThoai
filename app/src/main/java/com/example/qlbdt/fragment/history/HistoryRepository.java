package com.example.qlbdt.fragment.history;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {
    private MutableLiveData<List<History>> lstHistoryLiveData;
    private List<History> lstHistory;

    public HistoryRepository() {
        lstHistoryLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<History>> getLstHistoryLiveData(String userID) {
        setLstHistoryLiveData(userID);
        return lstHistoryLiveData;
    }

    private void setLstHistoryLiveData(String userID){
        lstHistory = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Histories")
                .whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            History history = document.toObject(History.class);
                            lstHistory.add(history);
                        }
                        lstHistoryLiveData.postValue(lstHistory);
                    }
                });
        lstHistoryLiveData.setValue(lstHistory);
    }
}
