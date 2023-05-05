package com.example.qlbdt.fragment.basket;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Basket.class}, version = 1)
public abstract class BasketDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "newBasket-database";
    private volatile static BasketDatabase instance;

    public static synchronized BasketDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), BasketDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().
                    build();
        }
        return instance;
    }

    public abstract BasketDao basketDao();
}
