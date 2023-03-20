package com.example.qlbdt.fFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qlbdt.R;
import com.example.qlbdt.fActivity.HomeActivity;
import com.example.qlbdt.fActivity.SmartphoneDetailActivity;
import com.example.qlbdt.fAdapter.BasketAdapter;
import com.example.qlbdt.fInterface.IRecyclerViewOnClickBasketItem;
import com.example.qlbdt.fObject.Basket;
import com.example.qlbdt.fOther.Notification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        });
        rcvBasket.setAdapter(adapter);
        return view;
    }

    private void ShowDialog(String name, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn thực sự muốn mua sản phẩm này?");
        builder.setPositiveButton("Tôi đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SendNoti(name);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String orderTime = dateFormat.format(calendar.getTime());

                String queryGetIDsmp = String.format("SELECT SmartphoneDetail.smartphone_detail_id, Smartphone.quantity " +
                        "FROM SmartphoneDetail JOIN Smartphone ON SmartphoneDetail.smartphone_id = Smartphone.smartphone_id " +
                        "WHERE name = '%s'", name);
                Cursor cursorIDsmp = HomeActivity.database.SelectData(queryGetIDsmp);
                cursorIDsmp.moveToFirst();
                int id_smp = cursorIDsmp.getInt(0);
                int quantity_smp = cursorIDsmp.getInt(1);

                String insertHistory = String.format("INSERT INTO History VALUES(NULL, '%s', %d, 1)", orderTime, id_smp);
                HomeActivity.database.QueryDatabase(insertHistory);

                quantity_smp -= 1;
                String updateQuantitySmartphone = String.format("UPDATE Smartphone SET quantity = %d WHERE name = '%s'", quantity_smp, name);
                HomeActivity.database.QueryDatabase(updateQuantitySmartphone);

                HomeActivity.database.DeleteBasket(index);

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

        String queryBasket = "SELECT Brand.name, Smartphone.name, Smartphone.price, Smartphone.avatar, Basket.basket_id " +
                "FROM Basket JOIN Smartphone ON Smartphone.smartphone_id = Basket.smartphone_id " +
                "JOIN Brand ON Brand.brand_id = Smartphone.brand_id";
        Cursor c = HomeActivity.database.SelectData(queryBasket);
        while(c.moveToNext()){
            String nB = c.getString(0);
            String nS = c.getString(1);
            String p = c.getString(2);
            byte[] i = c.getBlob(3);
            int id = c.getInt(4);
            baskets.add(new Basket(id, i, nB, nS, p));
        }
        tv_count_rcv_fragment_basket.setText("Tổng sản phẩm: " + baskets.size());
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