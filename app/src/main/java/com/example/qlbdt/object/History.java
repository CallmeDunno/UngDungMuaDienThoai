package com.example.qlbdt.object;

public class History {
    private int id;
    private String orderTime;
    private String color;
    private String nameCustomer;
    private String phoneNumber;
    private String email;
    private String address;
    private byte[] avatar;
    private String nameSmartPhone;
    private String priceSmartPhone;
    private String quantitySmartPhone;
    private byte[] imgSmartPhone;
    private String brandName;
    private String des;

    public History() {
    }

    public History(int id, String orderTime, String color, String nameCustomer, String nameSmartPhone, String priceSmartPhone, byte[] imgSmartPhone) {
        this.id = id;
        this.orderTime = orderTime;
        this.color = color;
        this.nameCustomer = nameCustomer;
        this.nameSmartPhone = nameSmartPhone;
        this.priceSmartPhone = priceSmartPhone;
        this.imgSmartPhone = imgSmartPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getNameSmartPhone() {
        return nameSmartPhone;
    }

    public void setNameSmartPhone(String nameSmartPhone) {
        this.nameSmartPhone = nameSmartPhone;
    }

    public String getPriceSmartPhone() {
        return priceSmartPhone;
    }

    public void setPriceSmartPhone(String priceSmartPhone) {
        this.priceSmartPhone = priceSmartPhone;
    }

    public String getQuantitySmartPhone() {
        return quantitySmartPhone;
    }

    public void setQuantitySmartPhone(String quantitySmartPhone) {
        this.quantitySmartPhone = quantitySmartPhone;
    }

    public byte[] getImgSmartPhone() {
        return imgSmartPhone;
    }

    public void setImgSmartPhone(byte[] imgSmartPhone) {
        this.imgSmartPhone = imgSmartPhone;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
