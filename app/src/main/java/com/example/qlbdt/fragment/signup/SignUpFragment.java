package com.example.qlbdt.fragment.signup;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qlbdt.R;
import com.example.qlbdt.activity.HomeActivity;
import com.example.qlbdt.databinding.FragmentSignUpBinding;
import com.example.qlbdt.fragment.login.LogInFragment;
import com.example.qlbdt.fragment.login.LoginViewModel;
import com.example.qlbdt.fragment.login.User;
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

    private FragmentSignUpBinding binding;
    private LoginViewModel loginViewModel;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private Calendar c;

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
        if (isValidated(email, password, phonenumber, address, dob)) {
            if (password.contentEquals(repassword)) {
                Map<String, Object> user = new HashMap<>();
                user.put("email", email);
                user.put("phoneNumber", phonenumber);
                user.put("dateOfBirth", dob);
                user.put("address", address);
                mAuth.createUserWithEmailAndPassword(email, password)
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
                                                loginViewModel.setSignUpUser(getContext(), email, password);
                                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                                startActivity(intent);
                                                getActivity().finish();
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
                        });
            } else {
                binding.edtRepassword.setError("invalid");
            }
        }
        progressDialog.dismiss();
    }

    private boolean isValidated(String email, String password, String phonenumber, String address, String dob) {
        if (TextUtils.isEmpty(email)) {
            binding.edtEmail.setError("Email is empty");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            binding.edtPassword.setError("Password is empty");
            return false;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            binding.edtPhonenumber.setError("Phonenumber is empty");
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            binding.edtAddress.setError("Address is empty");
            return false;
        }
        if (TextUtils.isEmpty(dob)) {
            binding.txtDOB.setError("DOB is empty");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.setError("Email is invalid");
            return false;
        }
        if (password.length() < 6) {
            binding.edtPassword.setError("Password is invalid");
            return false;
        }
        return true;
    }
}