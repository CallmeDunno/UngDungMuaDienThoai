package com.example.qlbdt.fragment.support;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qlbdt.databinding.FragmentSupportBinding;

public class SupportFragment extends Fragment {
    private FragmentSupportBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAction();
    }

    private void initAction() {
        binding.btnPhoneContact.setOnClickListener(view -> {
            Intent phone = new Intent();
            phone.setAction(Intent.ACTION_DIAL);
            phone.setData(Uri.parse("tel: (+84) 090999990"));
            requireContext().startActivity(phone);
        });

        binding.btnEmailContact.setOnClickListener(view -> {
            Intent mail = new Intent(Intent.ACTION_SENDTO);
            mail.setData(Uri.parse("mailto: email@gmail.com"));
            try {
                requireContext().startActivity(mail);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(requireContext(), "Not found Email application.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}