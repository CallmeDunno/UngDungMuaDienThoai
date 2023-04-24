package com.example.qlbdt.fFragment.Basket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.FragmentBasketBinding;
import com.example.qlbdt.databinding.FragmentHomeBinding;
import com.example.qlbdt.fOther.Notification;

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

    }
    private void initRecycleView() {
        binding.listviewgiohang.setLayoutManager(new LinearLayoutManager(getActivity()));
        basketAdapter = new BasketAdapter(getActivity(), this);
        binding.listviewgiohang.setAdapter(basketAdapter);
    }

    private void initViewModel() {
        viewmodel = new ViewModelProvider(this).get(BasketViewmodel.class);
        viewmodel.getListofbasketobserver().observe(requireActivity(), new Observer<List<Basket>>() {
            @Override
            public void onChanged(List<Basket> baskets) {
                if (baskets == null) {
                    binding.thongbaogiohang.setVisibility(View.VISIBLE);
                    binding.listviewgiohang.setVisibility(View.INVISIBLE);
                    EvenUltil(baskets);
                } else {
                    basketAdapter.setBasketlist(baskets);
                    binding.thongbaogiohang.setVisibility(View.INVISIBLE);
                    binding.listviewgiohang.setVisibility(View.VISIBLE);
                    EvenUltil(baskets);
                }
            }
        });
    }

    public void EvenUltil(List<Basket> baskets) {
        long tongtien = 0;
        for (int i = 0; i < baskets.size(); i++) {
            tongtien += baskets.get(i).getGiasp();
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

        int sl = basket.getSoluongsp();

        sl++;

        long slht = basket.getSoluongsp();

        long giaht = basket.getGiasp();

        basket.setSoluongsp(sl);

        long giamoi = (giaht * sl) / slht;

        basket.setGiasp(giamoi);

        viewmodel.Updatetbasket(basket);

    }


    @Override

    public void btngiamclick(Basket basket) {

        int sl = basket.getSoluongsp();

        sl--;

        long slht = basket.getSoluongsp();

        long giaht = basket.getGiasp();

        basket.setSoluongsp(sl);

        long giamoi = (giaht * sl) / slht;

        basket.setGiasp(giamoi);

        viewmodel.Updatetbasket(basket);

    }

    @Override

    public void btnxoaclick(Basket basket) {

        viewmodel.Deletebasket(basket);

    }
}
