package com.example.qlbdt.fObject;

public class Basket {

    private int idBasket;
    private byte[] imageSmp;
    private String nameBrand, nameSmp;
    private Price priceBasket;

    public Basket(int idBasket, byte[] imageSmp, String nameBrand, String nameSmp, String priceBasket) {
        this.idBasket = idBasket;
        this.imageSmp = imageSmp;
        this.nameBrand = nameBrand;
        this.nameSmp = nameSmp;
        this.priceBasket = new Price(priceBasket);
    }

    public int getIdBasket() {
        return idBasket;
    }

    public void setIdBasket(int idBasket) {
        this.idBasket = idBasket;
    }

    public byte[] getImageSmp() {
        return imageSmp;
    }

    public void setImageSmp(byte[] imageSmp) {
        this.imageSmp = imageSmp;
    }

    public String getNameBrand() {
        return nameBrand;
    }

    public void setNameBrand(String nameBrand) {
        this.nameBrand = nameBrand;
    }

    public String getNameSmp() {
        return nameSmp;
    }

    public void setNameSmp(String nameSmp) {
        this.nameSmp = nameSmp;
    }

    public String getPriceBasket() {
        return priceBasket.toString();
    }

    public void setPriceBasket(String priceBasket) {
        this.priceBasket = new Price(priceBasket);
    }
}
