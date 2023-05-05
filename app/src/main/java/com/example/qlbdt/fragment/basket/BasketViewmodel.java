package com.example.qlbdt.fragment.basket;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.qlbdt.activity.SplashScreenActivity;

import java.util.List;

public class BasketViewmodel extends AndroidViewModel {
    private final MutableLiveData<List<Basket>> listofbasket;
    private final BasketDatabase basketdatabase;

    public BasketViewmodel(Application application){
        super(application);
        listofbasket =new MutableLiveData<>();
        basketdatabase=BasketDatabase.getInstance(getApplication().getApplicationContext());

    }
    public MutableLiveData<List<Basket>> getListofbasketobserver(){
        return listofbasket;
    }
    public void Getallbasket(){
        String user = SplashScreenActivity.userDatabase.getString("currentUser", "");
        List<Basket> basketList= basketdatabase.basketDao().findBasketWithEmail(user);
        if(basketList.size()>0){
            listofbasket.postValue(basketList);
        }
        else {
            listofbasket.postValue(null);
        }
        listofbasket.setValue(basketList);
    }
    public void Insertbasket(Basket basket){
        basketdatabase.basketDao().InsertBasket(basket);
        Getallbasket();
    }
    public void Updatetbasket(Basket basket){
        basketdatabase.basketDao().UpdateBasket(basket);
        Getallbasket();
    }
    public void Deletebasket(Basket basket){
        basketdatabase.basketDao().DeleteBasket(basket);
        Getallbasket();
    }
}