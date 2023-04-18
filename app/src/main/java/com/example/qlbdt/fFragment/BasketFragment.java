package com.example.qlbdt.fFragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
import com.example.qlbdt.fOther.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BasketFragment extends Fragment {

    RecyclerView rcvBasket;
    BasketAdapter adapter;
    TextView tv_count_rcv_fragment_basket;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        tv_count_rcv_fragment_basket = view.findViewById(R.id.tv_count_rcv_fragment_basket);
        rcvBasket = view.findViewById(R.id.rcv_fragment_basket);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvBasket.setLayoutManager(linearLayoutManager);
        adapter = new BasketAdapter(getList(), new IRecyclerViewOnClickBasketItem() {
            @Override
            public void onClickItemBasket(Basket basket, int position) {
                ShowDialog(basket.getNameSmp(), position);
            }
        }, new IRecyclerViewOnClickDelete() {
            @Override
            public void onClickDetail(int index) {
                ShowDialogDeleteItem(index);
            }
        }, new IRecyclerViewOnClickDetail() {
            @Override
            public void onClickDetail(String name) {

            }
        });
        rcvBasket.setAdapter(adapter);
        return view;
    }

    private void ShowDialogDeleteItem(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?");
        builder.setPositiveButton("Tôi đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.setList(getList());
            }
        });
        builder.setNeutralButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private void ShowDialog(String name, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn thực sự muốn mua sản phẩm này?");
        builder.setPositiveButton("Tôi đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SendNoti(name);


                adapter.setList(getList());
            }
        });
        builder.setNeutralButton("Không, tôi không muốn mua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private List<Basket> getList() {
        List<Basket> baskets = new ArrayList<>();

        return baskets;
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