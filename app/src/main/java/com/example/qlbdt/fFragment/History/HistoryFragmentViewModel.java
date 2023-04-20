package com.example.qlbdt.fFragment.History;


public class HistoryFragmentViewModel {

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.qlbdt.BR;

public class HistoryFragmentViewModel extends BaseObservable {
    private String nameSmartPhone;
    private String priceSmartPhone;
    private String orderTime;
    private String color;

    @Bindable
    public String getNameSmartPhone() {
        return nameSmartPhone;
    }

    public void setNameSmartPhone(String nameSmartPhone) {
        this.nameSmartPhone = nameSmartPhone;
        notifyPropertyChanged(BR.nameSmartPhone);
    }

    @Bindable
    public String getPriceSmartPhone() {
        return priceSmartPhone;
    }

    public void setPriceSmartPhone(String priceSmartPhone) {
        this.priceSmartPhone = priceSmartPhone;
        notifyPropertyChanged(BR.priceSmartPhone);
    }

    @Bindable
    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
        notifyPropertyChanged(BR.orderTime);
    }

    @Bindable
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        notifyPropertyChanged(BR.color);
    }
}
