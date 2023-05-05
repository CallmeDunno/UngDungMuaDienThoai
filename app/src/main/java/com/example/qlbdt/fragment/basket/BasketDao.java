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
    void InsertBasket(Basket basket);

    @Update
    void UpdateBasket(Basket basket);

    @Delete
    void DeleteBasket(Basket basket);

    @Query("SELECT * FROM Basket WHERE userId LIKE :email ")
    List<Basket> findBasketWithEmail(String email);

}
