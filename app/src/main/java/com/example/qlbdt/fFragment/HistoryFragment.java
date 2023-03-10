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
        historyArrayList = new ArrayList<>();
        lstHistory = view.findViewById(R.id.lvHistoryKien);
        Cursor c =  HomeActivity.database.SelectData("select h.history_id, h.orderTime, smd.color, sp.name, sp.price, sp.avartar" +
                "from History h join Person p on p.person_id = h.person_id join SmartphoneDetail smd on smd.smartphone_detail_id = h.smartphone_detail_id " +
                "join Smartphone sp on sp.smartphone_id = smd.smartphone_id");
        while(c.moveToNext()){
            int id = c.getInt(0);
            String orderTime = c.getString(1);
            String color = c.getString(2);
            String nameSmartPhone = c.getString(3);
            String price = c.getString(4);
            byte[] avatar = c.getBlob(5);
            History history = new History(id,orderTime,color,nameSmartPhone,price,avatar);
            historyArrayList.add(history);
        }
        listAdapter = new HistoryAdapter(getActivity(), historyArrayList);
        lstHistory.setAdapter(listAdapter);
        return view;
    }
}