package com.example.qlbdt.fragment.login;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qlbdt.R;
import com.example.qlbdt.activity.HomeActivity;
import com.example.qlbdt.database.UserDatabase;
import com.example.qlbdt.databinding.FragmentLogInBinding;
import com.example.qlbdt.fragment.signup.SignUpFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;

public class LogInFragment extends Fragment {
    FragmentLogInBinding binding;
    LoginViewModel loginViewModel;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    ArrayList<String> userNames = new ArrayList<>();
    UserDatabase userDatabase;
    private GoogleSignInClient client;

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
        userDatabase = new UserDatabase(getContext());
        userNames = userDatabase.getUserNamesList();
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(getContext(), options);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, userNames);
        binding.edtEmail.setAdapter(adapter);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SignUpFragment());
            }
        });

        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginByGoogle();
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

        if(isValidated(email, password)) {
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                            loginViewModel.setUser(getContext(), email, password);
                            getActivity().finish();
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

    private boolean isValidated(String email, String password) {
        if(TextUtils.isEmpty(email)) {
            binding.edtEmail.setError("Email is empty");
            return false;
        }
        if(TextUtils.isEmpty(password)) {
            binding.edtPassword.setError("Password is empty");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.setError("Email is invalid");
            return false;
        }
        if(password.length() < 6) {
            binding.edtPassword.setError("Password is invalid");
            return false;
        }
        return true;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 123) {
//            if (resultCode == RESULT_OK) {
//                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//                try {
//                    GoogleSignInAccount account = task.getResult(ApiException.class);
//                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//                    mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                        @Override
//                        public void onSuccess(AuthResult authResult) {
//                            if (task.isSuccessful()) {
//                                startActivity(new Intent(requireContext(), HomeActivity.class));
//                                Toast.makeText(getContext(), "successfully", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//                } catch (ApiException e) {
//                    Log.d("oam", e.getMessage());
//                }
//            } else {
//
//            }
//        }
//    }


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                auth(account.getIdToken());
                progressDialog.dismiss();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
        }
    });
    private void auth(String token) {
        initData();
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if(task.isSuccessful()) {
                        loginViewModel.setGoogleUser(getContext(), task.getResult().getUser().getEmail(),
                                task.getResult().getUser().getPhoneNumber());
                        Toast.makeText(getContext(), "successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                        getActivity().finish();
                    }
                    else {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void loginByGoogle() {
            progressDialog.show();
            Intent i = client.getSignInIntent();
            activityResultLauncher.launch(i);
    }
}