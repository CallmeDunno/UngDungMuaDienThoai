package com.example.qlbdt.fFragment.Home;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ProductHome.class}, version = 1)
public abstract class ProductHomeDatabase extends RoomDatabase {
    public abstract IProductHomeDAO iProductHomeDAO();
}
