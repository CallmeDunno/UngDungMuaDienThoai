package com.example.qlbdt.fFragment.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.fAdapter.HistoryAdapter;
import com.example.qlbdt.fObject.History;

import java.util.List;

/**
 * Sơn
 * MVVM + Firebase
 * Thiết kế giao diện
 *
 * */

public class HistoryFragment extends Fragment {

    RecyclerView rcvHistory;
    HistoryAdapter historyAdapter;
    HistoryFragmentViewModel historyFragmentViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        rcvHistory = view.findViewById(R.id.rcvHistory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvHistory.setLayoutManager(linearLayoutManager);
        historyAdapter = new HistoryAdapter(requireContext());
        rcvHistory.setAdapter(historyAdapter);
        historyFragmentViewModel = new ViewModelProvider(this).get(HistoryFragmentViewModel.class);
        historyFragmentViewModel.getLstHistoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                historyAdapter.getData(histories);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (historyAdapter != null){
            historyAdapter.release();
        }
    }
}