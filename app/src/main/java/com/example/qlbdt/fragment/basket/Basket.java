package com.example.qlbdt.fragment.basket;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Basketsp")
public class Basket {
    @PrimaryKey
    public int idBasket;
    public String BasketName;
    public long BasketValue;
    public String BasketImage;
    public int BasketQuantity;

    public Basket(int idBasket, String basketName, long basketValue, String basketImage, int basketQuantity) {
        this.idBasket = idBasket;
        BasketName = basketName;
        BasketValue = basketValue;
        BasketImage = basketImage;
        BasketQuantity = basketQuantity;
    }

    public Basket() {

    }


    public int getIdBasket() {
        return idBasket;
    }

    public void setIdBasket(int idBasket) {
        this.idBasket = idBasket;
    }

    public String getBasketName() {
        return BasketName;
    }

    public void setBasketName(String basketName) {
        BasketName = basketName;
    }

    public long getBasketValue() {
        return BasketValue;
    }

    public void setBasketValue(long basketValue) {
        BasketValue = basketValue;
    }

    public String getBasketImage() {
        return BasketImage;
    }

    public void setBasketImage(String basketImage) {
        BasketImage = basketImage;
    }

    public int getBasketQuantity() {
        return BasketQuantity;
    }

    public void setBasketQuantity(int basketQuantity) {
        BasketQuantity = basketQuantity;
    }
}
