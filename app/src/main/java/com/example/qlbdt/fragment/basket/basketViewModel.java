package com.example.qlbdt.fragment.basket;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.qlbdt.activity.SplashScreenActivity;

import java.util.List;

public class basketViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Basket>> listBasket;
    private final BasketDatabase basketdatabase;

    public basketViewModel(Application application){
        super(application);
        listBasket =new MutableLiveData<>();
        basketdatabase=BasketDatabase.getInstance(getApplication().getApplicationContext());

    }
    public MutableLiveData<List<Basket>> getListBasketObserver(){
        return listBasket;
    }
    public void getAllBasket(){
        String user = SplashScreenActivity.userDatabase.getString("currentUser", "");
        List<Basket> basketList= basketdatabase.basketDao().findBasketWithEmail(user);
        if(basketList.size()>0){
            listBasket.postValue(basketList);
        }
        else {
            listBasket.postValue(null);
        }
        listBasket.setValue(basketList);
    }

    public void UpdateBasket(Basket basket){
        basketdatabase.basketDao().UpdateBasket(basket);
        getAllBasket();
    }
    public void DeleteBasket(Basket basket){
        basketdatabase.basketDao().DeleteBasket(basket);
        getAllBasket();
    }
}