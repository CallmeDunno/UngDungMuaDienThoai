package com.example.qlbdt.fragment.LogInPackage;

import android.util.Patterns;

public class User {
    private String email;
    private String phonenumber;
    private String DateOfBirth;
    private String address;
    private String password;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String phonenumber, String dateOfBirth, String address, String password) {
        this.email = email;
        this.phonenumber = phonenumber;
        DateOfBirth = dateOfBirth;
        this.address = address;
        this.password = password;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }


    public boolean isPasswordLengthGreaterThan5() {
        return getPassword().length() > 6;
    }
}
