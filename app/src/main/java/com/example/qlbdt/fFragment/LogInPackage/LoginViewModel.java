package com.example.qlbdt.fFragment.LogInPackage;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.example.qlbdt.BR;

public class LoginViewModel extends BaseObservable {
    private String email;
    private String password;
    public ObservableField<String> messageLogin = new ObservableField<>();

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void onClickLogin() {
        if(isValidated()) {
            messageLogin.set("success");
        }
    }

    public boolean isValidated() {
        if(email.isEmpty() || password.isEmpty()) {
            messageLogin.set("failed");
            return false;
        }
        return true;
    }
}
