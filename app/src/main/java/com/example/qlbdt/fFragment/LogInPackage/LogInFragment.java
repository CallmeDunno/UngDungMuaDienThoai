package com.example.qlbdt.fFragment.LogInPackage;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.FragmentLogInBinding;

public class LogInFragment extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentLogInBinding fragmentLogInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false);
        LoginViewModel loginViewModel = new LoginViewModel();
        fragmentLogInBinding.setLoginViewModel(loginViewModel);
        view = fragmentLogInBinding.getRoot();
        return view;
    }
}