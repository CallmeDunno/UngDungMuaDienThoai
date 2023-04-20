package com.example.qlbdt.fFragment.Home;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IProductHomeDAO {
    @Insert
    void insertProductHome(ProductHome productHome);

    @Query("Select id, quantity, name, price, image from Products")
    List<ProductHome> getListProductHome();

    @Query("Select * from Products")
    List<ProductHome> getListAllProductHome();
}
