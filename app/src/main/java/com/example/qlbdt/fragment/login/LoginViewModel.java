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
    ArrayList<String> usernames;
    UserDatabase userDatabase;
    FirebaseFirestore firestore;
    public MutableLiveData<User> userMutableLiveData;

    int k = 0;
    public LoginViewModel() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void setUser(Context context, String UserEmail, String UserPassword) {
        int j = 0;
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        userMutableLiveData.setValue(new User(UserEmail, UserPassword));

        userDatabase = new UserDatabase(context);
        usernames = userDatabase.getUserNamesList();
        userDatabase.saveCurrentUserName(UserEmail);

        for(int i = 0; i < usernames.size(); i++)
        {
            j = 0;
            if(usernames.get(i).contentEquals(UserEmail)) {
                j++;
            }
        }
        if(j == 0) {
            usernames.add(UserEmail);
            userDatabase.saveUserNames(usernames);
        }
    }

    public void setSignUpUser(Context context, String Useremail, String UserPassword) {
        userDatabase = new UserDatabase(context);
        userDatabase.saveCurrentUserName(Useremail);
    }

    public void setGoogleUser(Context context, String Useremail, String Userphonenumber) {
        userDatabase = new UserDatabase(context);
        firestore.collection("Users")
                .whereEqualTo("email", Useremail)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        k++;
                    }
                        if(k == 0) {
                            Map<String, Object> user = new HashMap<>();
                            user.put("email", Useremail);
                            user.put("phonenumber", Userphonenumber);
                            user.put("DateOfBirth", "");
                            user.put("address", "");
                            firestore.collection("Users")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("oam-gg", "successfully");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("oam-gg", "failed");
                                        }
                                    });
                        }
                        userDatabase.saveCurrentUserName(Useremail);
                    }
                });
        }

    public MutableLiveData<User> getCurrentUser(Context context) {
        userDatabase = new UserDatabase(context);
        List<User> users = new ArrayList<>();
        userMutableLiveData = new MutableLiveData<>();
        firestore.collection("Users")
                .whereEqualTo("email", userDatabase.getCurrentUserName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String email = doc.toObject(User.class).getEmail();
                            String phonenumber = doc.toObject(User.class).getPhonenumber();
                            String dob = doc.toObject(User.class).getDateOfBirth();
                            String address = doc.toObject(User.class).getAddress();
                            users.add(new User(email, phonenumber, dob, address));
                        }
                        userMutableLiveData.setValue(users.get(0));
                    }
                }
                });
        return userMutableLiveData;
    }

    public void clearUser(Context context) {
        userDatabase = new UserDatabase(context);
        userDatabase.clearCurrentUser();
    }
}
