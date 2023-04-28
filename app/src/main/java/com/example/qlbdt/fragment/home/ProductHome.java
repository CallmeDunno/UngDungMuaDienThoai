package com.example.qlbdt.fragment.home;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.qlbdt.object.Price;

import java.io.Serializable;

public final class ProductHome implements Serializable {
    private String id;
    private String name;
    private Price price;
    private String image;
    private String OS;
    private String battery;
    private String brand;
    private String color;
    private String cpu;
    private String description;
    private int quantity;
    private String ram;
    private String releaseTime;
    private String rom;
    private String type;
    private String weight;

    public ProductHome() {
    }

    public ProductHome(String id, String name, String price, String image, String OS, String battery, String brand, String color, String cpu, String description, int quantity, String ram, String releaseTime, String rom, String type, String weight) {
        this.id = id;
        this.name = name;
        this.price = new Price(price);
        this.image = image;
        this.OS = OS;
        this.battery = battery;
        this.brand = brand;
        this.color = color;
        this.cpu = cpu;
        this.description = description;
        this.quantity = quantity;
        this.ram = ram;
        this.releaseTime = releaseTime;
        this.rom = rom;
        this.type = type;
        this.weight = weight;
    }

    public ProductHome(String name, String price, String imageProduct, String type) {
        this.name = name;
        this.price = new Price(price);
        this.image = imageProduct;
        this.type = type;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final DiffUtil.ItemCallback<ProductHome> PRODUCT_HOME_DIFF_UTIL = new DiffUtil.ItemCallback<ProductHome>() {

        @Override
        public boolean areItemsTheSame(@NonNull ProductHome oldItem, @NonNull ProductHome newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductHome oldItem, @NonNull ProductHome newItem) {
            return oldItem.id.equals(newItem.id) && oldItem.type.equals(newItem.type) && oldItem.quantity == newItem.quantity;
        }
    };
}
