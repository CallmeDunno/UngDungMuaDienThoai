package com.example.qlbdt.fEnum;

public enum EStringQuery {
    SelectAllData ("Select * from db");

    public String getQuery() {
        return query;
    }

    EStringQuery(String query) {
        this.query = query;
    }

    private String query;


}
