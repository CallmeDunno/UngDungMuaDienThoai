package com.example.qlbdt.fInterface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.qlbdt.object.Basket;

import java.util.List;

@Dao
public interface IBasketDao {
    @Insert
    void Inserthang(Basket basket);
    @Delete
    void Deletehang(Basket basket);
    @Query("Select * from Giohang")
    List<Basket> getAll();
    @Update
    void Updatehang(Basket basket);
}