package com.example.qlbdt.fragment.basket;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

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
        List<Basket> basketList= basketdatabase.basketDao().getAllBasket();
        if(basketList.size()>0){
            listofbasket.postValue(basketList);
        }
        else {
            listofbasket.postValue(null);
        }
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