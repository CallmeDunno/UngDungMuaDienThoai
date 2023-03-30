package com.example.qlbdt.fFragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qlbdt.R;
import com.example.qlbdt.fActivity.HomeActivity;
import com.example.qlbdt.fActivity.SmartphoneDetailActivity;
import com.example.qlbdt.fAdapter.PhotoAdapter;
import com.example.qlbdt.fAdapter.SmartPhoneHomeAdapter;
import com.example.qlbdt.fInterface.IRecyclerViewOnClick;
import com.example.qlbdt.fObject.Photo;
import com.example.qlbdt.fObject.Smartphone;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mListPhoto;
    private Timer mTimer;  // Sử lý tác vụ sau bao lâu thì chuyển slide
    /*
    * 12/3 Tuan commit
    * */

    private RecyclerView rcvPhone;
    private SmartPhoneHomeAdapter mSmartPhoneHomeAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ viewPager và circleIndiacator
        viewPager = view.findViewById(R.id.viewpager_tuan);
        circleIndicator = view.findViewById(R.id.circle_indicator_tuan);

        // khởi tạo photoAdapter
        mListPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(this, mListPhoto);       // (biến môi trường và mảng dữ liệu)
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSlideImages();

        //Commit 17/3

        rcvPhone = view.findViewById(R.id.rcv_phone);
        mSmartPhoneHomeAdapter = new SmartPhoneHomeAdapter(getActivity(), new IRecyclerViewOnClick() {
            @Override
            public void onClickItemSmartphone(Smartphone smartphone) {
                Intent intent = new Intent(getActivity(), SmartphoneDetailActivity.class);
                intent.putExtra("NameSmartphone", smartphone.getName());
                startActivity(intent);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);  //Số cột muốn hiển thị
        rcvPhone.setLayoutManager(gridLayoutManager);

        mSmartPhoneHomeAdapter.setData(getListPhone());    //Set dữ liệu cho adapter
        rcvPhone.setAdapter(mSmartPhoneHomeAdapter);

        return view;
    }

    // Lấy dữ liệu
    List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.banner1));
        list.add(new Photo(R.drawable.banner2));
        list.add(new Photo(R.drawable.banner3));
        list.add(new Photo(R.drawable.banner4));
        list.add(new Photo(R.drawable.banner5));
        return list;
    }

    private List<Smartphone> getListPhone(){
        List<Smartphone> list = new ArrayList<>();
        Cursor c = HomeActivity.database.SelectData("SELECT Smartphone.name, Smartphone.price, Smartphone.quantity, Smartphone.avatar FROM Smartphone");
        while (c.moveToNext()){
            String n = c.getString(0);
            String p = c.getString(1);
            int q = c.getInt(2);
            byte[] a = c.getBlob(3);
            Smartphone phone = new Smartphone(n, p, q, a);
            list.add(phone);
        }
        return list;
    }

    private void autoSlideImages(){
        if (mListPhoto == null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }

        // Khởi tạo timer
        if (mTimer == null){
            mTimer = new Timer();
        }

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();   // index trong ViewPager
                        int totalItem = mListPhoto.size() - 1;
                        if (currentItem < totalItem){
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);      // Set current cho ViewPager
                        }else {
                            viewPager.setCurrentItem(0);                // Nếu đến ảnh cuối r thì sẽ set về ảnh ban đầu
                        }
                    }
                });
            }
        }, 500, 3000);  // (delay 0,5s; xử lý mỗi tác vụ : 3s)
    }

    // Nếu cái activity này k tồn tại thì cần Cancel Timer đi
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSmartPhoneHomeAdapter.setData(getListPhone());
    }
}