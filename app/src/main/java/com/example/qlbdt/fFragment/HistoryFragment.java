package com.example.qlbdt.fFragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.qlbdt.R;
import com.example.qlbdt.fActivity.HomeActivity;
import com.example.qlbdt.fAdapter.HistoryAdapter;
import com.example.qlbdt.fDatabase.MyDatabase;
import com.example.qlbdt.fObject.History;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private ArrayList<History> historyArrayList;
    private HistoryAdapter listAdapter;
    private ListView lstHistory;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        Cursor c =  HomeActivity.database.SelectData("select * \n" +
                "from History h join Person p on p.idPerson = h.idHistory join SmartphoneDetails smd on smd.idDetails = h.idDetails " +
                "join SmartPhone sp on sp.idSmartPhone = smd.idSmartPhone");
        historyArrayList = new ArrayList<>();
        historyArrayList.add(new History(1, "10:00:00","vàng","Iphone 13 promax","12.000.000"));
        listAdapter = new HistoryAdapter(getActivity(), historyArrayList);
        lstHistory = view.findViewById(R.id.lvHistoryKien);
        lstHistory.setAdapter(listAdapter);
        return view;
    }
}