package com.example.qlbdt.fFragment.Basket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
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
  private int i=4;
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
        binding.btnttgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewmodel.Insertbasket(new Basket(1,"Kiski",2000,"1.png",2));
            }
        });
        binding.btnttmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewmodel.Insertbasket(new Basket(2,"Kiskier",5000,"1.png",5));

            }
        });
    }
    private void initRecycleView() {
        binding.listviewgiohang.setLayoutManager(new LinearLayoutManager(getActivity()));
        basketAdapter = new BasketAdapter(getActivity(), this);
        binding.listviewgiohang.setAdapter(basketAdapter);
        List<Basket> baskets=BasketDatabase.getInstance(getActivity()).basketDao().getAllBasket();
        if(baskets.size()<=0){
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

    private void initViewModel() {
        viewmodel = new ViewModelProvider(this).get(BasketViewmodel.class);
      //  viewmodel.Insertbasket(new Basket(4,"Kiski",2000,"1.png",2));
        viewmodel.getListofbasketobserver().observe(getViewLifecycleOwner(), new Observer<List<Basket>>() {
            @Override
            public void onChanged(List<Basket> baskets) {

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


                }

        });
    }

    public void EvenUltil(List<Basket> baskets) {
        long tongtien = 0;
        for (int i = 0; i < baskets.size(); i++) {
            tongtien += baskets.get(i).getBasketValue();
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

            int sl = basket.getBasketQuantity();

            sl++;

            long slht = basket.getBasketQuantity();

            long giaht = basket.getBasketValue();

            basket.setBasketQuantity(sl);

            long giamoi = (giaht * sl) / slht;

            basket.setBasketValue(giamoi);

            viewmodel.Updatetbasket(basket);
    }


    @Override

    public void btngiamclick(Basket basket) {

            int sl = basket.getBasketQuantity();

            sl--;

            long slht = basket.getBasketQuantity();

            long giaht = basket.getBasketValue();

            basket.setBasketQuantity(sl);

            long giamoi = (giaht * sl) / slht;

            basket.setBasketValue(giamoi);

            viewmodel.Updatetbasket(basket);
    }

    @Override

    public void btnxoaclick(Basket basket) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Basket");
        builder.setMessage("Do you want to deleete this basket?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               viewmodel.Deletebasket(basket);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

}
