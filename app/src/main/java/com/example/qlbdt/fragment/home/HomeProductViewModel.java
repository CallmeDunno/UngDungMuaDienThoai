package com.example.qlbdt.fragment.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class HomeProductViewModel extends ViewModel {
    private HomeProductRepository repository;

    public HomeProductViewModel(){
        repository = new HomeProductRepository();
    }

    public MutableLiveData<List<HomeProduct>> getListHomeProductLiveData(){
        return repository.getListHomeProductLiveData();
    }
}
