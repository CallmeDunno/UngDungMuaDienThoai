package com.example.qlbdt.fObject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.qlbdt.fInterface.IBasketDao;

@Database(entities = {Basket.class},version = 1)
public abstract class Basketdatabase extends RoomDatabase {
    private static final String DATABASE_NAME="basket.db";
    private static Basketdatabase instance;
    public static synchronized Basketdatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),Basketdatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract IBasketDao iBasketDao();
}