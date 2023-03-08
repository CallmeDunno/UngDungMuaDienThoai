package com.example.qlbdt.fObject;

public class Smartphone {
    private String name;
    private String price;
    private int quantity;
    private byte[] avatar;

    public Smartphone(String name, String price, int quantity, byte[] avatar) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.avatar = avatar;
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

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
