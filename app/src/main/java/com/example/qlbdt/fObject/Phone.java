package com.example.qlbdt.fObject;

public class Phone {
    private int resouceImage;
    private String name;
    private String price;
    private int quantity;

    public Phone(int resouceImage, String name, String price, int quantity) {
        this.resouceImage = resouceImage;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getResouceImage() {
        return resouceImage;
    }

    public void setResouceImage(int resouceImage) {
        this.resouceImage = resouceImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
