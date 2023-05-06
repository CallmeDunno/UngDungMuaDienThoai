package com.example.qlbdt.fragment.login;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlbdt.activity.HomeActivity;
import com.example.qlbdt.database.UserDatabase;
import com.example.qlbdt.fragment.home.HomeProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoginViewModel extends ViewModel {
    private ArrayList<String> usernames;
    private UserDatabase userDatabase;


    public void setUser(Context context, String UserEmail, String UserPassword) {
        int j = 0;
        userDatabase = new UserDatabase(context);
        usernames = userDatabase.getUserNamesList();
        userDatabase.saveCurrentUserName(UserEmail);

        for (int i = 0; i < usernames.size(); i++) {
            j = 0;
            if (usernames.get(i).contentEquals(UserEmail)) {
                j++;
            }
        }
        if (j == 0) {
            usernames.add(UserEmail);
            userDatabase.saveUserNames(usernames);
        }
    }

    public void setSignUpUser(Context context, String Useremail, String UserPassword) {
        userDatabase = new UserDatabase(context);
        userDatabase.saveCurrentUserName(Useremail);
    }

    public void clearUser(Context context) {
        userDatabase = new UserDatabase(context);
        userDatabase.clearCurrentUser();
    }

}
