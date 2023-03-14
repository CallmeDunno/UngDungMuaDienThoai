package com.example.qlbdt.fEnum;

public enum EStringQuery {

    CreateTableBrand ("CREATE TABLE IF NOT EXISTS Brand (brand_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR(200) NOT NULL)"),
    CreateTableSmartphone ("CREATE TABLE IF NOT EXISTS Smartphone (smartphone_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR(200) NOT NULL," +
            "price VARCHAR(200) NOT NULL," +
            "quantity INTEGER NOT NULL," +
            "avatar BLOB NOT NULL," +
            "brand_id INTEGER," +
            "FOREIGN KEY (brand_id) REFERENCES Brand(brand_id)" +
            ")"),
    CreateTableSmartphoneDetail ("CREATE TABLE IF NOT EXISTS SmartphoneDetail (smartphone_detail_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "description TEXT NOT NULL," +
            "CPU VARCHAR(200) NOT NULL," +
            "RAM VARCHAR(200) NOT NULL," +
            "ROM VARCHAR(200) NOT NULL," +
            "color VARCHAR(200) NOT NULL," +
            "battery VARCHAR(200) NOT NULL," +
            "weight VARCHAR(200) NOT NULL," +
            "warrantyPeriod VARCHAR(200) NOT NULL," +
            "smartphone_id INTEGER NOT NULL," +
            "FOREIGN KEY (smartphone_id) REFERENCES Smartphone(smartphone_id)" +
            ")"),
    CreateTablePerson ("CREATE TABLE IF NOT EXISTS Person (person_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR(200) NOT NULL," +
            "phone VARCHAR(200) NOT NULL," +
            "email VARCHAR(200) NOT NULL," +
            "address TEXT NOT NULL," +
            "avatar BLOB" +
            ")"),
    CreateTableHistory ("CREATE TABLE IF NOT EXISTS History (history_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "orderTime VARCHAR(200) NOT NULL," +
            "smartphone_detail_id INTEGER NOT NULL," +
            "person_id INTEGER NOT NULL," +
            "FOREIGN KEY (smartphone_detail_id) REFERENCES SmartphoneDetail(smartphone_detail_id)," +
            "FOREIGN KEY (person_id) REFERENCES Person(person_id))");

    public String getQuery() {
        return query;
    }

    EStringQuery(String query) {
        this.query = query;
    }

    private String query;


}
