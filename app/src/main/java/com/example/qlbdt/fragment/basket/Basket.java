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
    public String basketName;
    public long basketPrice;
    public int numberOrder;
    public String basketImg;
    public String brandName;
    public String price; //đơn giá

    public Basket(@NonNull String id,
                  @NonNull String userId,
                  String basketName,
                  String basketPrice,
                  int numberOrder,
                  String basketImg,
                  String brandName) {
        this.id = id;
        this.userId = userId;
        this.basketName = basketName;

        this.price = basketPrice;
        Price p = new Price(basketPrice);
        this.basketPrice = p.toLong() * numberOrder;

        this.numberOrder = numberOrder;
        this.basketImg = basketImg;
        this.brandName = brandName;
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

    public String getBasketName() {
        return basketName;
    }

    public long getBasketPrice() {
        return basketPrice;
    }

    public void setBasketPrice(long basketPrice) {
        this.basketPrice = basketPrice;
    }

    public int getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }

    public String getBasketImg() {
        return basketImg;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
