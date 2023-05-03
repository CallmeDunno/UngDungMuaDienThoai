package com.example.qlbdt.fragment.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.qlbdt.R;
import com.example.qlbdt.database.UserDatabase;
import com.example.qlbdt.databinding.FragmentAccountBinding;
import com.example.qlbdt.fragment.login.LoginViewModel;
import com.example.qlbdt.fragment.login.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tiến Dũng
 * */

public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;
    List<User> users = new ArrayList<>();
    ProgressDialog progressDialog;

    UserDatabase userDatabase;
    FirebaseAuth mAuth;
    FirebaseUser fbUser;
    FirebaseFirestore firestore;

    String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        userDatabase = new UserDatabase(getContext());
        progressDialog = new ProgressDialog(getContext());
        setEditable(false);
        binding.txtEmail.setEnabled(false);
        initData();
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditable(true);
                String useremail = binding.txtEmail.getText().toString().trim();
                String userphonenumber = binding.txtSDT.getText().toString().trim();
                String userdob = binding.txtDOB.getText().toString().trim();
                String useraddress = binding.txtAddress.getText().toString().trim();
                updateUser(useremail, userphonenumber, userdob, useraddress);
            }
        });
    }
     private void initData() {
        progressDialog.show();
        users.clear();
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
                                 id = doc.getId();
                                 Log.d("oam", doc.getId());
                             }
                             Log.d("oam", String.valueOf(users.isEmpty()));
                             if(fbUser.getPhotoUrl() != null) {
                                 Glide.with(getContext()).load(fbUser.getPhotoUrl()).into(binding.avatarQA);
                             }
                             binding.txtEmail.setText(users.get(0).getEmail());
                             binding.txtDOB.setText(users.get(0).getDateOfBirth());
                             binding.txtAddress.setText(users.get(0).getAddress());
                             binding.txtSDT.setText(users.get(0).getPhonenumber());
                             progressDialog.dismiss();
                         } else {
                             Log.d("oam-failed", "null");
                         }
                     }
                 });
     }
     private void updateUser(String Useremail, String Userphonenumber, String Userdob, String Useraddress) {
         Map<String, Object> user = new HashMap<>();
         user.put("email", Useremail);
         user.put("phonenumber", Userphonenumber);
         user.put("DateOfBirth", Userdob);
         user.put("address", Useraddress);
        firestore.collection("Users")
                .document(id)
                .update(user)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Log.d("oam", "updated");
                    } else {
                        Log.d("oam", "unupdated");
                    }
                });
     }

     private void setEditable(boolean editable) {
        binding.txtDOB.setEnabled(editable);
        binding.txtAddress.setEnabled(editable);
        binding.txtSDT.setEnabled(editable);
     }

}