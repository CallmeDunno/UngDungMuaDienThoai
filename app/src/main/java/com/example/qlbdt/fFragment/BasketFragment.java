package com.example.qlbdt.fFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.fAdapter.BasketAdapter;
import com.example.qlbdt.fInterface.IRecyclerViewOnClickBasketItem;
import com.example.qlbdt.fInterface.IRecyclerViewOnClickDelete;
import com.example.qlbdt.fInterface.IRecyclerViewOnClickDetail;
import com.example.qlbdt.fObject.Basket;
import com.example.qlbdt.fObject.Basketdatabase;
import com.example.qlbdt.fOther.Notification;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BasketFragment extends Fragment {

    ListView lvgiohang;
    public static TextView txtthongbao;
    public static TextView txttongtien;
    Button btnthanhtoan, btnttmua;
    Toolbar toolbargiohang;
    public static BasketAdapter giohangadapterB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        lvgiohang = view.findViewById(R.id.listviewgiohang);
        txtthongbao = view.findViewById(R.id.thongbaogiohang);
        txttongtien = view.findViewById(R.id.txttongtien);
        btnthanhtoan = view.findViewById(R.id.btnttgiohang);
        btnttmua = view.findViewById(R.id.btnttmuahang);
        toolbargiohang = view.findViewById(R.id.toolbargiohang);

        giohangadapterB = new BasketAdapter(getActivity(),(ArrayList<Basket>) Basketdatabase.getInstance(getActivity()).iBasketDao().getAll(),this);
        lvgiohang.setAdapter(giohangadapterB);
        CheckData();
        EvenUltil();
        EvenButton();

        return view;
    }

    private void EvenButton() {
        btnttmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void EvenUltil(){
        long tongtien = 0;

        for (int i = 0; i<Basketdatabase.getInstance(getActivity()).iBasketDao().getAll().size() ; i++){
            tongtien += Basketdatabase.getInstance(getActivity()).iBasketDao().getAll().get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + "VND");
    }
    private void CheckData(){
        if(Basketdatabase.getInstance(getActivity()).iBasketDao().getAll().size() <=0){
            giohangadapterB.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }else{
            giohangadapterB.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }


    private void SendNoti(String name) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo_app);
        String content = "Chúc mừng bạn đã mua thành công điện thoại " + name;
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getActivity(), Notification.CHANNEL_ID)
                .setContentTitle("Thông báo")
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setSmallIcon(R.drawable.ic_smartphone)
                .setLargeIcon(bitmap);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

        notificationManager.notify(GetTimeNoti(), notification.build());

    }

    //gửi noti nhiều lần
    private int GetTimeNoti() {
        return (int) new Date().getTime();
    }
}