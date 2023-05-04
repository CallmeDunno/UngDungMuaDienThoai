package com.example.qlbdt.fragment.basket;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.qlbdt.object.Price;

@Entity(primaryKeys = {"id", "userId"})
public class Basket {
    @ColumnInfo
    @NonNull
    public String id;
    @ColumnInfo
    @NonNull
    public String userId;
    public String Basketname;
    public long Basketprice;
    public int numberOrder;
    public String Basketimg;
    public String BasketbrandName;

    public String price; //đơn giá

    public Basket(@NonNull String id,
                  @NonNull String userId,
                  String basketname,
                  String basketprice,
                  int numberOrder,
                  String basketimg,
                  String basketbrandName) {
        this.id = id;
        this.userId = userId;
        this.Basketname = basketname;

        this.price = basketprice;
        Price p = new Price(basketprice);
        this.Basketprice = p.toLong() * numberOrder;

        this.numberOrder = numberOrder;
        this.Basketimg = basketimg;
        this.BasketbrandName = basketbrandName;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
