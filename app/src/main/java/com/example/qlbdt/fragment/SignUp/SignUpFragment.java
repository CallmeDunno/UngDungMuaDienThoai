package com.example.qlbdt.fragment.SignUp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.FragmentSignUpBinding;
import com.example.qlbdt.activity.HomeActivity;
import com.example.qlbdt.fragment.LogInPackage.LogInFragment;
import com.example.qlbdt.fragment.LogInPackage.LoginViewModel;
import com.example.qlbdt.fragment.LogInPackage.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    FragmentSignUpBinding binding;
    LoginViewModel loginViewModel;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    Calendar c ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new LogInFragment());
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        binding.txtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                DatePickerDialog bdDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        binding.txtDOB.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
                    }
                }, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
                bdDialog.show();
            }
        });
    }
    private void initData() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_login, fragment);
        fragmentTransaction.commit();
    }
    private void signup() {
        progressDialog.show();
        initData();
        String email = binding.edtEmail.getText().toString().trim();
        String phonenumber = binding.edtPhonenumber.getText().toString().trim();
        String dob = binding.txtDOB.getText().toString().trim();
        String address = binding.edtAddress.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        String repassword = binding.edtRepassword.getText().toString().trim();
        if(password.contentEquals(repassword)) {
            loginViewModel.setUser(email, phonenumber, dob, address, password);

            Map<String, Object> user = new HashMap<>();
            user.put("email", email);
            user.put("password", password);
            user.put("phonenumber", phonenumber);
            user.put("dob", dob);
            user.put("address", address);
            loginViewModel.getUser().observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if(isValidated(user)) {
                        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        firestore.collection("Users")
                                                .add(user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getActivity(), "Signup successfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getActivity(), "Signup failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity(), "Check your information", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            binding.edtRepassword.setError("invalid");
        }
        progressDialog.dismiss();
    }
    private boolean isValidated(User user) {
        if(TextUtils.isEmpty(user.getEmail())) {
            binding.edtEmail.setError("Email is empty");
            return false;
        }
        if(TextUtils.isEmpty(user.getPassword())) {
            binding.edtPassword.setError("Password is empty");
            return false;
        }
        if(TextUtils.isEmpty(user.getPhonenumber())) {
            binding.edtPhonenumber.setError("Phonenumber is empty");
            return false;
        }
        if(TextUtils.isEmpty(user.getAddress())) {
            binding.edtAddress.setError("Address is empty");
            return false;
        }
        if(TextUtils.isEmpty(user.getDateOfBirth())) {
            binding.txtDOB.setError("DOB is empty");
            return false;
        }
        if(!user.isEmailValid()) {
            binding.edtEmail.setError("Email is invalid");
            return false;
        }
        if(!user.isPasswordLengthGreaterThan5()) {
            binding.edtPassword.setError("Password is invalid");
            return false;
        }
        return true;
    }
}