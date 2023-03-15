package com.example.qlbdt.fObject;

import java.io.Serializable;

public class Support implements Serializable {

    public String getNameSp() {
        return nameSp;
    }

    public void setNameSp(String nameSp) {
        this.nameSp = nameSp;
    }

    public String getPhoneSp() {
        return phoneSp;
    }

    public void setPhoneSp(String phoneSp) {
        this.phoneSp = phoneSp;
    }

    public String getMailSp() {
        return mailSp;
    }

    public void setMailSp(String mailSp) {
        this.mailSp = mailSp;
    }


    public int getImageSp() {
        return imageSp;
    }

    public void setImageSp(int imageSp) {
        this.imageSp = imageSp;
    }

    private int imageSp;
    private String nameSp;
    private String phoneSp;
    private String mailSp;


    public Support(int imageSp,String nameSp, String phoneSp, String mailSp ){
        this.imageSp = imageSp;
        this.nameSp = nameSp;
        this.phoneSp = phoneSp;
        this.mailSp = mailSp;
    }

}
