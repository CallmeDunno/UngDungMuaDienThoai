package com.example.qlbdt.fInterface;

import android.database.Cursor;

public interface IDatabase {
    void QueryDatabase(String query);
    Cursor SelectData(String query);
    void InsertDatabaseWithImage(String query);
}
