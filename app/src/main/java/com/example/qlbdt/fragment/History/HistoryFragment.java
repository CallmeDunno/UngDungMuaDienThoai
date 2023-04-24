package com.example.qlbdt.fragment.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.adapter.HistoryAdapter;
import com.example.qlbdt.object.History;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Sơn
 * MVVM + Firebase
 * Thiết kế giao diện
 *
 * - Làm lại cái class object History
 * - Giao diện sửa lại
 * - Sử dụng View Binding
 * */

public class HistoryFragment extends Fragment {

    RecyclerView rcvHistory;
    List<History> lstHistory;
    HistoryAdapter historyAdapter;
    HistoryFragmentViewModel historyFragmentViewModel;
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lstHistory = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("Histories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> lst = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:lst){
                    History history = doc.toObject(History.class);
                    lstHistory.add(history);
                }
                historyFragmentViewModel.setValueLstHistoryLiveData(lstHistory);
            }
        });
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        rcvHistory = view.findViewById(R.id.rcvHistory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvHistory.setLayoutManager(linearLayoutManager);
        historyFragmentViewModel = new ViewModelProvider(this).get(HistoryFragmentViewModel.class);
        historyFragmentViewModel.getLstHistoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                historyAdapter = new HistoryAdapter(histories);
                rcvHistory.setAdapter(historyAdapter);
            }
        });
        return view;
    }

}