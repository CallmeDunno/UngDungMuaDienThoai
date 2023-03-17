package com.example.qlbdt.fFragment;

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
import com.example.qlbdt.fAdapter.PhotoAdapter;
import com.example.qlbdt.fAdapter.SmartPhoneHomeAdapter;
import com.example.qlbdt.fObject.Phone;
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
    private Timer mTimer;
    /*
    * 12/3 Tuan commit
    * */

    private RecyclerView rcvPhone;
    private SmartPhoneHomeAdapter mSmartPhoneHomeAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewpager_tuan);
        circleIndicator = view.findViewById(R.id.circle_indicator_tuan);

        mListPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(this, mListPhoto);
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSlideImages();

        //Commit 17/3

        rcvPhone = view.findViewById(R.id.rcv_phone);
        mSmartPhoneHomeAdapter = new SmartPhoneHomeAdapter(getActivity());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rcvPhone.setLayoutManager(gridLayoutManager);

        mSmartPhoneHomeAdapter.setData(getListPhone());
        rcvPhone.setAdapter(mSmartPhoneHomeAdapter);

        return view;
    }

    List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.banner1));
        list.add(new Photo(R.drawable.banner2));
        list.add(new Photo(R.drawable.banner3));
        list.add(new Photo(R.drawable.banner4));
        list.add(new Photo(R.drawable.banner5));
        return list;
    }

    private List<Phone> getListPhone(){
        List<Phone> list = new ArrayList<>();
        Cursor c = HomeActivity.database.SelectData("SELECT Smartphone.name, Smartphone.price, Smartphone.quantity, Smartphone.avatar FROM Smartphone");
        while (c.moveToNext()){
            String n = c.getString(0);
            String p = c.getString(1);
            int q = c.getInt(2);
            byte[] a = c.getBlob(3);
            Phone phone = new Phone(a, n, p, q);
            list.add(phone);
        }
        return list;
    }

    private void autoSlideImages(){
        if (mListPhoto == null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }

        // Init timer
        if (mTimer == null){
            mTimer = new Timer();
        }

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if (currentItem < totalItem){
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }
}