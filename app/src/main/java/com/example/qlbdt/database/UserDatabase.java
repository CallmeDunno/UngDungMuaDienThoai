package com.example.qlbdt.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserDatabase {
    private SharedPreferences userDatabase;
    private Gson gson;

    public UserDatabase(Context context) {
        userDatabase = context.getSharedPreferences("userDatabase", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    //Lưu tên người dùng đăng nhập
    public void saveUserNames(ArrayList<String> users) {
        SharedPreferences.Editor editor = userDatabase.edit();
        editor.putString("username", gson.toJson(users));
        editor.apply();
    }


    //Lưu tên của người dùng hiện tại
    public void saveCurrentUserName(String Useremail) {
        SharedPreferences.Editor editor = userDatabase.edit();
        editor.putString("currentUser", Useremail);
        editor.apply();
    }

    public String getCurrentUserName() {
        String Useremail = userDatabase.getString("currentUser", null);
        return Useremail;
    }

    public ArrayList<String> getUserNamesList() {
        String userString = userDatabase.getString("username", null);
        Type userListType = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> userList = gson.fromJson(userString, userListType);

        if (userList != null) return userList;
        else return new ArrayList<>();
    }

    public void clearCurrentUser() {
        SharedPreferences.Editor editor = userDatabase.edit();
        editor.putString("currentUser", null);
        editor.apply();
    }

}