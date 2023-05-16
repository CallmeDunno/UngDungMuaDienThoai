package com.example.qlbdt.fragment.product_detail;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlbdt.fragment.home.HomeProduct;

public class ProductDetailViewModel extends ViewModel {
    private ProductDetailRepository repository;

    public ProductDetailViewModel(){
        repository = new ProductDetailRepository();
    }

    public MutableLiveData<HomeProduct> getProductHomeMutableLiveData(String documentPath){
        return repository.getProductHomeMutableLiveData(documentPath);
    }

    public void pushHistory(String userID, HomeProduct homeProduct, int quantity){
        repository.pushHistory(userID, homeProduct, quantity);
    }

    public void addToCart(Context context, String userID, HomeProduct homeProduct, int quantity){
        repository.addToCart(context, userID, homeProduct, quantity);
    }
}
