package com.example.qlbdt.fFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.qlbdt.R;
import com.example.qlbdt.fAdapter.SupportAdapter;
import com.example.qlbdt.fObject.Support;

import java.util.ArrayList;

public class SupportFragment extends Fragment {
    ListView listViewSupport;
    ArrayList<Support> lstSupport;
    SupportAdapter supportAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_support, container, false);
        listViewSupport = view.findViewById(R.id.lsv_Sp);
        lstSupport = new ArrayList<>();
        lstSupport.add(new Support(R.drawable.avt_dung,"Nguyễn Quốc Dũng","0923767834","QDung@gmail.com"));
        lstSupport.add(new Support(R.drawable.avt_qa,"Nguyễn Quỳnh Anh","09123445667","QuynhAnh@gmail.com"));
        lstSupport.add(new Support(R.drawable.avt_oanh,"Hà Hoàng Oanh","0934789221","Oanh@gmail.com"));
        lstSupport.add(new Support(R.drawable.avt_tuan,"Nguyễn Bá Tuấn","051234567","Tuan@gmail.com"));
        lstSupport.add(new Support(R.drawable.avt_kien,"Ngô Trung Kiên","0223876541","NgoKien@gmail.com"));
        supportAdapter = new SupportAdapter(getActivity(),R.layout.member_support, lstSupport);
        listViewSupport.setAdapter(supportAdapter);

        return view;
    }
}