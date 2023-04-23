package com.example.qlbdt.fFragment.LogInPackage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.FragmentLogInBinding;
import com.example.qlbdt.fActivity.HomeActivity;
import com.example.qlbdt.fFragment.SignUp.SignUpFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInFragment extends Fragment {
    FragmentLogInBinding binding;
    LoginViewModel loginViewModel;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                login();
            }
        });

        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SignUpFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_login, fragment);
        fragmentTransaction.commit();
    }

    private void initData() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void login() {
        initData();
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        loginViewModel.setUser(email, password);
        loginViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (isValidated(user)) {
                    mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getActivity(), user.getEmail(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    private boolean isValidated(User user) {
        if (TextUtils.isEmpty(user.getEmail())) {
            binding.edtEmail.setError("Email is empty");
            return false;
        }
        if (TextUtils.isEmpty(user.getPassword())) {
            binding.edtPassword.setError("Password is empty");
        }
        if (!user.isEmailValid()) {
            binding.edtEmail.setError("Email is invalid");
            return false;
        }
        if (!user.isPasswordLengthGreaterThan5()) {
            binding.edtPassword.setError("Password is invalid");
        }
        return true;
    }


}