package com.example.qlbdt.fragment.basket;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"id","userId"})
public class Basket {
    @ColumnInfo @NonNull
    public String  id;
    @ColumnInfo @NonNull
    public String userId;

    public String Basketname;
    public long Basketprice;
    public int numberOrder;
    public String Basketimg;
    public String BasketbrandName;
    public String color;
    public String orderTime;

    public Basket(@NonNull String id, @NonNull String userId, String basketname, long basketprice, int numberOrder, String basketimg, String basketbrandName, String color, String orderTime) {
        this.id = id;
        this.userId = userId;
        Basketname = basketname;
        Basketprice = basketprice;
        this.numberOrder = numberOrder;
        Basketimg = basketimg;
        BasketbrandName = basketbrandName;
        this.color = color;
        this.orderTime = orderTime;
    }

    public Basket() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getBasketname() {
        return Basketname;
    }

    public void setBasketname(String basketname) {
        Basketname = basketname;
    }

    public long getBasketprice() {
        return Basketprice;
    }

    public void setBasketprice(long basketprice) {
        Basketprice = basketprice;
    }

    public int getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }

    public String getBasketimg() {
        return Basketimg;
    }

    public void setBasketimg(String basketimg) {
        Basketimg = basketimg;
    }

    public String getBasketbrandName() {
        return BasketbrandName;
    }

    public void setBasketbrandName(String basketbrandName) {
        BasketbrandName = basketbrandName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}
