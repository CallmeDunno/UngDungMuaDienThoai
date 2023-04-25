package com.example.qlbdt.object;

public class History {
    private int id;
    private String userId;
    private String nameSmartPhone;
    private String priceSmartPhone;
    private int numberOrder;
    private String imgSmartPhone;
    private String brandName;
    private String color;
    private String orderTime;

    public History() {
    }

    public History(int id, String userId, String nameSmartPhone, String priceSmartPhone, int numberOrder, String imgSmartPhone, String brandName, String color, String orderTime) {
        this.id = id;
        this.userId = userId;
        this.nameSmartPhone = nameSmartPhone;
        this.priceSmartPhone = priceSmartPhone;
        this.numberOrder = numberOrder;
        this.imgSmartPhone = imgSmartPhone;
        this.brandName = brandName;
        this.color = color;
        this.orderTime = orderTime;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }

    public String getImgSmartPhone() {
        return imgSmartPhone;
    }

    public void setImgSmartPhone(String imgSmartPhone) {
        this.imgSmartPhone = imgSmartPhone;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
