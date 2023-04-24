package com.example.qlbdt.fInterface;

import android.content.Context;
import android.database.Cursor;

import com.example.qlbdt.fObject.User;

public interface IDatabase {
    void QueryDatabase(String query);
    Cursor SelectData(String query);
    void InsertUser(User u);
    void UpdateUser(User u);
    void InsertBasket(int indexSmp);
    void DeleteBasket(int index);
    void InsertSmartphone(Context context, String name, String price, long quantity, int avatar, long brand_id);
}
