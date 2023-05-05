package com.example.qlbdt.fragment.basket;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BasketDao {
    @Query("Select * from Basket")
    List<Basket> getAllBasket();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBasket(Basket basket);

    @Update
    void updateBasket(Basket basket);

    @Delete
    void deleteBasket(Basket basket);

    @Query("SELECT * FROM Basket WHERE userId LIKE :email ")
    List<Basket> findBasketWithEmail(String email);

}
