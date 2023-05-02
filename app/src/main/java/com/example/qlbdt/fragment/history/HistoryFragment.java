package com.example.qlbdt.fragment.history;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.qlbdt.activity.SplashScreenActivity;
import com.example.qlbdt.databinding.FragmentHistoryBinding;

import java.util.Collections;
import java.util.List;

/**
 * Sơn
 * MVVM + Firebase
 * Thiết kế giao diện
 *
 * - Làm lại cái class object History
 * - Giao diện sửa lại
 * - Sử dụng View Binding
 * */

public class HistoryFragment extends Fragment {

    private HistoryAdapter historyAdapter;
    private HistoryViewModel historyViewModel;
    private FragmentHistoryBinding binding;
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
        initAction();
    }

    private void initAction() {
        historyAdapter.setClickButtonHistory(this::handleClickRepurchaseButton);
    }

    private void handleClickRepurchaseButton(String userID) {
        HistoryFragmentDirections.ActionHistoryFragmentToProductDetailFragment action =
                HistoryFragmentDirections.actionHistoryFragmentToProductDetailFragment();
        action.setDocumentPath(userID);
        Navigation.findNavController(requireView()).navigate(action);
    }

    private void initViewModel() {
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        String userID = SplashScreenActivity.userDatabase.getString("username", "");
        historyViewModel.setUserID(userID);
        historyViewModel.getLstHistoryLiveData().observe(requireActivity(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                if (histories.size() == 0){
                    progressDialog.show();
                } else {
                    Collections.sort(histories, new History.SortByDateTime());
                    historyAdapter.submitList(histories);
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void initView() {
        progressDialog = new ProgressDialog(requireContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.rcvHistory.setLayoutManager(linearLayoutManager);

        historyAdapter = new HistoryAdapter();
        binding.rcvHistory.setAdapter(historyAdapter);
    }
}