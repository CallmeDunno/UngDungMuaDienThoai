package com.example.qlbdt.object;

public class User {
    private String name;
    private String phone;
    private String email;
    private String address;
    private byte[] avatar;

    public User(String name, String phone, String email, String address, byte[] avatar) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
