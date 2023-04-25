package com.example.qlbdt.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.qlbdt.fragment.login.User;
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

    public void saveUserNames(ArrayList<String> users) {
        SharedPreferences.Editor editor = userDatabase.edit();
        editor.putString("username", gson.toJson(users));
        editor.apply();
    }

    public void setCurrentUser(User user) {
        SharedPreferences.Editor editor = userDatabase.edit();
        String JsonUser = gson.toJson(user);
        editor.putString("user", JsonUser);
        editor.apply();
    }

    public User getCurrentUser() {
        SharedPreferences.Editor editor = userDatabase.edit();
        User user = gson.fromJson("user", User.class);
        return user;
    }

    public ArrayList<String> getUserNamesList() {
        String userString = userDatabase.getString("username", null);
        Type userListType = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> userList = gson.fromJson(userString, userListType);

        if (userList != null) return userList;
        else return new ArrayList<>();
    }

}