package com.example.qlbdt.fragment.LogInPackage;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> phonenumber = new MutableLiveData<>();
    public MutableLiveData<String> dob = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private Context context;
    public MutableLiveData<User> userMutableLiveData;
    FirebaseAuth mAuth;

    public void setUser(String UserEmail, String UserPassword) {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        userMutableLiveData.setValue(new User(UserEmail, UserPassword));
    }

    public void setUser( String UserEmail, String Userphonenumber, String Userdob, String Useraddress, String Userpassword) {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        userMutableLiveData.setValue(new User(UserEmail, Userphonenumber, Userdob, Useraddress,Userpassword));
    }
    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }




}
