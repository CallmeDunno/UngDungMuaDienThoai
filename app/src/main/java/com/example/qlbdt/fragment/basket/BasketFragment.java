package com.example.qlbdt.fragment.basket;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.FragmentBasketBinding;
import com.example.qlbdt.other.Notification;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Tuấn
 * Room + MVVM
 * Thiết kế giao diện
 */

public class BasketFragment extends Fragment implements BasketAdapter.HandleBasketClick {
    //Hello
    private BasketViewmodel viewmodel;
    private BasketAdapter basketAdapter;

    private FragmentBasketBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBasketBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecycleView();
        binding.btnttgiohang.setOnClickListener(view1 -> {

        });
        binding.btnttmuahang.setOnClickListener(view12 -> {


        });
    }
    @SuppressLint("SetTextI18n")
    private void initRecycleView() {
        binding.listviewgiohang.setLayoutManager(new LinearLayoutManager(getActivity()));
        basketAdapter = new BasketAdapter(getContext(), this);
        binding.listviewgiohang.setAdapter(basketAdapter);
        List<Basket> baskets= BasketDatabase.getInstance(getActivity()).basketDao().getAllBasket();
        if(baskets.size() == 0){
            binding.thongbaogiohang.setVisibility(View.VISIBLE);
            binding.listviewgiohang.setVisibility(View.INVISIBLE);
            binding.txttongtien.setText("O VND");
        }
        else{
            basketAdapter.setBasketlist(baskets);
            binding.thongbaogiohang.setVisibility(View.INVISIBLE);
            binding.listviewgiohang.setVisibility(View.VISIBLE);
            EvenUltil(baskets);
        }

    }

    @SuppressLint("SetTextI18n")
    private void initViewModel() {
        viewmodel = new ViewModelProvider(this).get(BasketViewmodel.class);
        //  viewmodel.Insertbasket(new Basket(4,"Kiski",2000,"1.png",2));
        viewmodel.getListofbasketobserver().observe(getViewLifecycleOwner(), baskets -> {

            if (baskets==null) {
                binding.thongbaogiohang.setVisibility(View.VISIBLE);
                binding.listviewgiohang.setVisibility(View.INVISIBLE);
                binding.txttongtien.setText("O VND");
            } else {
                basketAdapter.setBasketlist(baskets);
                binding.thongbaogiohang.setVisibility(View.INVISIBLE);
                binding.listviewgiohang.setVisibility(View.VISIBLE);
                EvenUltil(baskets);

            }


        });
    }

    public void EvenUltil(List<Basket> baskets) {
        long tongtien = 0;
        for (int i = 0; i < baskets.size(); i++) {
            tongtien += baskets.get(i).getBasketprice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.txttongtien.setText(String.format("%sVND", decimalFormat.format(tongtien)));
    }

    //TODO: KHÔNG XÓA 2 HÀM BÊN DƯỚI
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

    @Override

    public void btntangclick(Basket basket) {

        int sl = basket.getNumberOrder();

        sl++;

        long slht = basket.getNumberOrder();

        long giaht = basket.getBasketprice();

        basket.setNumberOrder(sl);

        long giamoi = (giaht * sl) / slht;

        basket.setBasketprice(giamoi);

        viewmodel.Updatetbasket(basket);
    }


    @Override

    public void btngiamclick(Basket basket) {

        int sl = basket.getNumberOrder();

        sl--;

        long slht = basket.getNumberOrder();

        long giaht = basket.getBasketprice();

        basket.setNumberOrder(sl);

        long giamoi = (giaht * sl) / slht;

        basket.setBasketprice(giamoi);

        viewmodel.Updatetbasket(basket);
    }

    @Override

    public void btnxoaclick(Basket basket) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Basket");
        builder.setMessage("Do you want to deleete this basket?");
        builder.setPositiveButton("Yes", (dialog, which) -> viewmodel.Deletebasket(basket));
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.show();
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        List<Basket> baskets= BasketDatabase.getInstance(getActivity()).basketDao().getAllBasket();
        if(baskets.size() == 0){
            binding.thongbaogiohang.setVisibility(View.VISIBLE);
            binding.listviewgiohang.setVisibility(View.INVISIBLE);
            binding.txttongtien.setText("O VND");
        }
        else{
            basketAdapter.setBasketlist(baskets);
            binding.thongbaogiohang.setVisibility(View.INVISIBLE);
            binding.listviewgiohang.setVisibility(View.VISIBLE);
            EvenUltil(baskets);
        }

    }
}
