package com.example.qlbdt.fObject;

import java.util.Comparator;

public class Smartphone {
    private String name;
    private Price price;
    private int quantity;
    private byte[] avatar;

    public Smartphone(String name, String price, int quantity, byte[] avatar) {
        this.name = name;
        this.price = new Price(price);
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
        return price.toString();
    }

    public void setPrice(String price) {
        this.price = new Price(price);
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

    public static class NameOrder implements Comparator<Smartphone> {
        @Override
        public int compare(Smartphone a, Smartphone b) {
            return a.getName().compareTo(b.getName());
        }
    }

    public static class PriceOrderAsc implements Comparator<Smartphone>{
        @Override
        public int compare(Smartphone a, Smartphone b) {
            return a.getPrice().compareTo(b.getPrice());
        }
    }

    public static class PriceOrderDesc implements Comparator<Smartphone>{
        @Override
        public int compare(Smartphone a, Smartphone b) {
            return b.getPrice().compareTo(a.getPrice());
        }
    }
}
