package com.example.qlbdt.fFragment.Basket;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class BasketViewmodel extends AndroidViewModel {
    private MutableLiveData<List<Basket>> listofbasket;
    private Basketdatabase basketdatabase;

    public BasketViewmodel(Application application){
        super(application);
        listofbasket =new MutableLiveData<>();
        basketdatabase=Basketdatabase.getInstance(getApplication().getApplicationContext());
    }
    public MutableLiveData<List<Basket>> getListofbasketobserver(){
        return listofbasket;
    }
    public void Getallbasket(){
        List<Basket> basketList= basketdatabase.iBasketDao().getAllBasket();
        if(basketList.size()>0){
            listofbasket.postValue(basketList);
        }
        else {
            listofbasket.postValue(null);
        }
    }
    public void Insertbasket(Basket basket){
        basketdatabase.iBasketDao().InsertBasket(basket);
        Getallbasket();
    }
    public void Updatetbasket(Basket basket){
        basketdatabase.iBasketDao().UpdateBasket(basket);
        Getallbasket();
    }
    public void Deletebasket(Basket basket){
        basketdatabase.iBasketDao().DeleteBasket(basket);
        Getallbasket();
    }
}