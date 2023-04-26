package com.example.qlbdt.object;

import java.util.Comparator;

public class Smartphone {
    private String name;
    private Price price;
    private int quantity;

    private String brand_name;

    public Smartphone(String name, String price, int quantity) {
        this.name = name;
        this.price = new Price(price);
        this.quantity = quantity;
    }

    public Smartphone(String name, String price, int quantity, byte[] avatar, String brand_name) {
        this.name = name;
        this.price = new Price(price);
        this.quantity = quantity;
        this.brand_name = brand_name;
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

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
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
            return a.price.compareTo(b.price);
        }
    }

    public static class PriceOrderDesc implements Comparator<Smartphone>{
        @Override
        public int compare(Smartphone a, Smartphone b) {
            return b.price.compareTo(a.price);
        }
    }
}
