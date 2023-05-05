package com.example.qlbdt.fragment.product_detail;

public enum Status {
    DONE("Done"),
    FAIL("Fail"),
    ERROR("Error"),
    NONE("None");

    Status(String status) {
        this.status = status;
    }
    private String status;

    public String getStatus() {
        return status;
    }
}
