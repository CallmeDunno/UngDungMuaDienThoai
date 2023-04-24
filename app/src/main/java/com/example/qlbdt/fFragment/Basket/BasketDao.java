package com.example.qlbdt.fFragment.Basket;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BasketDao {
    @Query("Select * from Basketsp")
    List<Basket> getAllBasket();
    @Insert
    void InsertBasket(Basket basket);
    @Update
    void UpdateBasket(Basket basket);
    @Delete
    void DeleteBasket(Basket basket);

}
