package com.example.qlbdt.fFragment.History;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlbdt.fObject.History;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragmentViewModel extends ViewModel {
    private MutableLiveData<List<History>> lstHistoryLiveData;
    private List<History> lstHistory;
    FirebaseFirestore db;

    public HistoryFragmentViewModel(){
        lstHistoryLiveData = new MutableLiveData<>();
        initData();
    }

    private void initData(){
        lstHistory = new ArrayList<>();
        //lstHistory.add(new History(1,"1","IPhone 14 ProMax","25.000.000",1,"","Apple","Tím","29 Tháng 3 2023"));
        db = FirebaseFirestore.getInstance();
        db.collection("Histories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> lst = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:lst){
                    History history = doc.toObject(History.class);
                    lstHistory.add(history);
                }
                lstHistoryLiveData.postValue(lstHistory);
            }
        });
        lstHistoryLiveData.setValue(lstHistory);
    }

    public MutableLiveData<List<History>> getLstHistoryLiveData() {
        return lstHistoryLiveData;
    }

    public void setValueLstHistoryLiveData(List<History> lst){
        lstHistoryLiveData.setValue(lst);
    }

    public void Add(History history){
        lstHistory.add(history);
        lstHistoryLiveData.setValue(lstHistory);
    }

}
