package com.example.qlbdt.fFragment.LogInPackage;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlbdt.fDatabase.UserDatabase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> phonenumber = new MutableLiveData<>();
    public MutableLiveData<String> dob = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    ArrayList<String> usernames;
    UserDatabase userDatabase;
    FirebaseFirestore firestore;
    public MutableLiveData<User> userMutableLiveData;

    public LoginViewModel() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void setUser(String UserEmail, String UserPassword) {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        userMutableLiveData.setValue(new User(UserEmail, UserPassword));
    }

    public void setUser(String UserEmail, String Userphonenumber, String Userdob, String Useraddress, String Userpassword) {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        userMutableLiveData.setValue(new User(UserEmail, Userphonenumber, Userdob, Useraddress, Userpassword));
    }

    public MutableLiveData<User> getUser() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void saveCurrentUser(Context context, String email) {
        User[] user = new User[1];
        int j = 0;
        userDatabase = new UserDatabase(context);
        usernames = userDatabase.getUserNamesList();
        for(int i = 0; i < usernames.size(); i++)
        {
            j = 0;
            if(usernames.get(i).contentEquals(email)) {
                j++;
            }
        }
        if(j == 0) {
            usernames.add(email);
            userDatabase.saveUserNames(usernames);
        }
            firestore.collection("Users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(task -> {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String uemail = doc.toObject(User.class).getEmail();
                            String uphonenumber = doc.toObject(User.class).getPhonenumber();
                            String udob = doc.toObject(User.class).getDateOfBirth();
                            String uaddress = doc.toObject(User.class).getAddress();
                            user[0] = new User(uemail, uphonenumber, udob, uaddress);
                        }
                    });
            userDatabase.setCurrentUser(user[0]);
        Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
    }

    public User getCurrentUser(Context context) {
        userDatabase = new UserDatabase(context);
        User user = userDatabase.getCurrentUser();
        return user;
    }
}
