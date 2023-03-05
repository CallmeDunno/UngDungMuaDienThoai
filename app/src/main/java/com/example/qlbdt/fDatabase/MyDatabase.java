package com.example.qlbdt.fDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.qlbdt.fEnum.EStringQuery;
import com.example.qlbdt.fInterface.IDatabase;

public class MyDatabase extends SQLiteOpenHelper implements IDatabase {

    public MyDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EStringQuery.CreateTableBrand.getQuery());
        sqLiteDatabase.execSQL(EStringQuery.CreateTableSmartphone.getQuery());
        sqLiteDatabase.execSQL(EStringQuery.CreateTableSmartphoneDetail.getQuery());
        sqLiteDatabase.execSQL(EStringQuery.CreateTableImageDetail.getQuery());
        sqLiteDatabase.execSQL(EStringQuery.CreateTablePerson.getQuery());
        sqLiteDatabase.execSQL(EStringQuery.CreateTableHistory.getQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void QueryDatabase(String query) {

    }

    @Override
    public Cursor SelectData(String query) {
        return null;
    }

    @Override
    public void InsertDatabaseWithImage(String query) {
//        SQLiteDatabase database = getWritableDatabase();
//        //biên dịch sau nên để là dấu chấm hỏi
//        String sql = "insert into SOMETHING values(null, ?, ?, ?)";
//        //Biên dịch câu lệnh sql
//        SQLiteStatement statement = database.compileStatement(sql);
//        //phần nào đã ràng buộc dữ liệu rồi thì sẽ clear đi
//        statement.clearBindings();
//        //trong câu lệnh sql, đánh số từ null - 0, ? - 1, ....
//        statement.bindString(1, name);
//        statement.bindString(2, describe);
//        statement.bindBlob(3, image);
//        //thực thi câu lệnh sql sau khi biên dịch
//        statement.executeInsert();
    }

}
