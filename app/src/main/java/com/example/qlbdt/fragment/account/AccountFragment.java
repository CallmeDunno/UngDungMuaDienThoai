package com.example.qlbdt.fragment.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bumptech.glide.Glide;
import com.example.qlbdt.R;
import com.example.qlbdt.activity.HomeActivity;
import com.example.qlbdt.databinding.FragmentAccountBinding;
import com.example.qlbdt.databinding.FragmentProductDetailBinding;
import com.example.qlbdt.fragment.login.LoginViewModel;
import com.example.qlbdt.fragment.login.User;
import com.example.qlbdt.fragment.product_detail.ProductDetailViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Tiến Dũng
 * */

public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;
    LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String userEmail = currentUser.getEmail();
            loginViewModel.getUser(userEmail).observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    binding.emailQA.setText(user.getEmail());
                    binding.phoneQA.setText(user.getPhonenumber());
                    binding.addressQA.setText(user.getAddress());
                    binding.nameQA.setText(user.getDateOfBirth());
                }
            });

        return binding.getRoot();
    }
}

