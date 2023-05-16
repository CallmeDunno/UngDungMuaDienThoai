package com.example.qlbdt.fragment.history;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class HistoryViewModel extends ViewModel {
    private HistoryRepository repository;

    public HistoryViewModel() {
        repository = new HistoryRepository();
    }

    public MutableLiveData<List<History>> getListHistory(String user){
        return repository.getLstHistoryLiveData(user);
    }
}
