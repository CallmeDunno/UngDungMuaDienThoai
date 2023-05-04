package com.example.qlbdt.fragment.history;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.qlbdt.object.Price;
import com.example.qlbdt.object.DateTime;

import java.io.Serializable;
import java.util.Comparator;

public class History implements Serializable {
    private String userID;
    private String productID;
    private Price price;  //Đơn giá
    private Long totalMoney; //Tổng tiền
    private String image;
    private String name;
    private String brand;
    private int quantity;  //Số lượng mua
    private DateTime timeToBuy;

    public History() {
    }

    public History(String userID,
                   String productID,
                   String price,
                   String image,
                   String name,
                   String brand,
                   int quantity,
                   String timeToBuy) {
        this.userID = userID;
        this.productID = productID;
        this.price = new Price(price);
        this.totalMoney = this.price.toLong() * quantity;
        this.image = image;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;

        String[] fields = timeToBuy.split(" ");
        String date = fields[0];
        String time = fields[1];
        this.timeToBuy = new DateTime(date, time);
    }

    public History(String price, int quantity){
        this.price = new Price(price);
        this.quantity = quantity;
        this.totalMoney = this.price.toLong() * quantity;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getPrice() {
        return price.toString();
    }

    public void setPrice(String price) {
        this.price = new Price(price);
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTimeToBuy() {
        return timeToBuy.toString();
    }

    public void setTimeToBuy(String timeToBuy) {
        String[] fields = timeToBuy.split(" ");
        String date = fields[0];
        String time = fields[1];
        this.timeToBuy = new DateTime(date, time);
    }

    public static class SortByDateTime implements Comparator<History> {
        @Override
        public int compare(History h1, History h2) {
            return h1.timeToBuy.compareTo(h2.timeToBuy);
        }
    }

    public static final DiffUtil.ItemCallback<History> HISTORY_DIFF_UTIL = new DiffUtil.ItemCallback<History>() {
        @Override
        public boolean areItemsTheSame(@NonNull History oldItem, @NonNull History newItem) {
            return oldItem.productID.equals(newItem.productID);
        }

        @Override
        public boolean areContentsTheSame(@NonNull History oldItem, @NonNull History newItem) {
            return oldItem.productID.equals(newItem.productID) &&
                    oldItem.brand.equals(newItem.productID) &&
                    oldItem.image.equals(newItem.image) &&
                    oldItem.name.equals(newItem.name) &&
                    oldItem.totalMoney.equals(newItem.totalMoney) &&
                    oldItem.timeToBuy.equals(newItem.timeToBuy) &&
                    oldItem.quantity == newItem.quantity &&
                    oldItem.price.toString().equals(newItem.price.toString());
        }
    };
}
