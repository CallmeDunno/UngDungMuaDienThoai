package com.example.qlbdt.fragment.account;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.qlbdt.database.UserDatabase;
import com.example.qlbdt.databinding.FragmentAccountBinding;
import com.example.qlbdt.fragment.login.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tiến Dũng
 */

public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    private List<User> users = new ArrayList<>();
    private ProgressDialog progressDialog;
    private UserDatabase userDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser fbUser;
    private FirebaseFirestore firestore;
    private Calendar c;
    private String id;

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
        userDatabase = new UserDatabase(requireContext());
        progressDialog = new ProgressDialog(getContext());
        setEditable(false);
        binding.txtEmail.setEnabled(false);
        initData();

        binding.txtDOB.setOnClickListener(view1 -> {
            c = Calendar.getInstance();
            DatePickerDialog bdDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view1, int year, int month, int dayOfMonth) {
                    binding.txtDOB.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
                }
            }, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            bdDialog.show();
        });
        binding.btnEdit.setOnClickListener(view12 -> {
            setEditable(true);
            binding.btnEdit.setVisibility(View.INVISIBLE);
            binding.llButton.setVisibility(View.VISIBLE);
        });
        binding.btnHuy.setOnClickListener(view13 -> {
            binding.llButton.setVisibility(View.INVISIBLE);
            binding.btnEdit.setVisibility(View.VISIBLE);
            setEditable(false);
            initView(users.get(0));
        });
        binding.btnLuu.setOnClickListener(view14 -> {
            String useremail = binding.txtEmail.getText().toString().trim();
            String userphonenumber = binding.txtSDT.getText().toString().trim();
            String userdob = binding.txtDOB.getText().toString().trim();
            String useraddress = binding.txtAddress.getText().toString().trim();
            updateUser(useremail, userphonenumber, userdob, useraddress);
            binding.llButton.setVisibility(View.INVISIBLE);
            binding.btnEdit.setVisibility(View.VISIBLE);
            setEditable(false);
        });
    }

    private void initData() {
        progressDialog.show();
        users.clear();
        firestore.collection("Users")
                .whereEqualTo("email", userDatabase.getCurrentUserName())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String email = doc.toObject(User.class).getEmail();
                            String phonenumber = doc.toObject(User.class).getPhoneNumber();
                            String dob = doc.toObject(User.class).getDateOfBirth();
                            String address = doc.toObject(User.class).getAddress();
                            users.add(new User(email, phonenumber, dob, address));
                            id = doc.getId();
                        }
                        if (fbUser.getPhotoUrl() != null) {
                            Glide.with(requireContext()).load(fbUser.getPhotoUrl()).into(binding.avatarQA);
                        }
                        initView(users.get(0));
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUser(String Useremail, String Userphonenumber, String Userdob, String Useraddress) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", Useremail);
        user.put("phoneNumber", Userphonenumber);
        user.put("dateOfBirth", Userdob);
        user.put("address", Useraddress);
        firestore.collection("Users")
                .document(id)
                .update(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Update fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setEditable(boolean editable) {
        binding.txtDOB.setEnabled(editable);
        binding.txtAddress.setEnabled(editable);
        binding.txtSDT.setEnabled(editable);
    }

    private void initView(User currentuser) {
        binding.txtEmail.setText(currentuser.getEmail());
        binding.txtDOB.setText(currentuser.getDateOfBirth());
        binding.txtAddress.setText(currentuser.getAddress());
        binding.txtSDT.setText(currentuser.getPhoneNumber());
    }

}