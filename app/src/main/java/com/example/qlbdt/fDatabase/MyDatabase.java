package com.example.qlbdt.fDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.Nullable;

import com.example.qlbdt.fEnum.EStringQuery;
import com.example.qlbdt.fInterface.IDatabase;
import com.example.qlbdt.fObject.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyDatabase extends SQLiteOpenHelper implements IDatabase {

    public MyDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EStringQuery.CreateTableBrand.getQuery());
        sqLiteDatabase.execSQL(EStringQuery.CreateTableSmartphone.getQuery());
        sqLiteDatabase.execSQL(EStringQuery.CreateTableSmartphoneDetail.getQuery());
        sqLiteDatabase.execSQL(EStringQuery.CreateTablePerson.getQuery());
        sqLiteDatabase.execSQL(EStringQuery.CreateTableHistory.getQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void QueryDatabase(String query) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(query);
        database.close();
    }

    @Override
    public Cursor SelectData(String query) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(query, null);
    }

    @Override
    public void InsertUser(User u) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Person VALUES(null, ?, ?, ?, ?, ?)";
        //Biên dịch câu lệnh sql
        SQLiteStatement statement = database.compileStatement(sql);
        //phần nào đã ràng buộc dữ liệu rồi thì sẽ clear đi
        statement.clearBindings();
        //trong câu lệnh sql, đánh số từ null - 0, ? - 1, ....
        statement.bindString(1, u.getName());
        statement.bindString(2, u.getPhone());
        statement.bindString(3, u.getEmail());
        statement.bindString(4, u.getAddress());
        statement.bindBlob(5, u.getAvatar());
        //thực thi câu lệnh sql sau khi biên dịch
        statement.executeInsert();
    }

    @Override
    public void UpdateUser(User u) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE Person SET name = ?, phone = ?, address = ?, email = ?, avatar = ? WHERE person_id = 1";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, u.getName());
        statement.bindString(2, u.getPhone());
        statement.bindString(3, u.getAddress());
        statement.bindString(4, u.getEmail());
        statement.bindBlob(5, u.getAvatar());
        statement.executeUpdateDelete();
    }

    @Override
    public void InsertSmartphone(Context context, String name, String price, long quantity, int avatar, long brand_id) {
        InputStream inputStream = context.getResources().openRawResource(avatar);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while (true) {
            try {
                if ((len = inputStream.read(buffer)) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteArrayOutputStream.write(buffer, 0, len);
        }
        byte[] avt = byteArrayOutputStream.toByteArray();

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Smartphone VALUES(null, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, name);
        statement.bindString(2, price);
        statement.bindLong(3, quantity);
        statement.bindBlob(4, avt);
        statement.bindLong(5, brand_id);
        statement.executeInsert();
    }


}
