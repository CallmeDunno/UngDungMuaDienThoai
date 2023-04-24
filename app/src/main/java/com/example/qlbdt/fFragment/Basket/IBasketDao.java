package com.example.qlbdt.fFragment.Basket;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.qlbdt.fFragment.Basket.Basket;

import java.util.List;

@Dao
public interface IBasketDao {
    @Insert
    void InsertBasket(Basket basket);
    @Delete
    void DeleteBasket(Basket basket);
    @Query("Select * from Basket")
    List<Basket> getAllBasket();
    @Update
    void UpdateBasket(Basket basket);
}