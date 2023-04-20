package com.example.qlbdt.fFragment.Home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.FragmentHomeBinding;
import com.example.qlbdt.fAdapter.PhotoAdapter;
import com.example.qlbdt.fInterface.IRecyclerViewOnClick;
import com.example.qlbdt.fObject.Photo;
import com.example.qlbdt.fObject.Smartphone;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Dungx
 * */

public class HomeFragment extends Fragment {
    private List<Photo> mListPhoto;
    private Timer mTimer;
    private ProductHomeAdapter productHomeAdapter;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initSlide();
        initUI();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getListProductHomeLiveData().observe(getActivity(), new Observer<List<ProductHome>>() {
            @Override
            public void onChanged(List<ProductHome> productHomes) {
                productHomeAdapter.setData(productHomes);
            }
        });

        return view;
    }

    private void initUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rcvPhone.setLayoutManager(linearLayoutManager);
        productHomeAdapter = new ProductHomeAdapter(getActivity(), new IRecyclerViewOnClick() {
            @Override
            public void onClickItemSmartphone(Smartphone smartphone) {

            }
        });
        binding.rcvPhone.setAdapter(productHomeAdapter);
    }

    private void initSlide() {
        mListPhoto = getListPhoto();
        PhotoAdapter photoAdapter = new PhotoAdapter(this, mListPhoto);
        binding.viewpager.setAdapter(photoAdapter);
        binding.circleIndicator.setViewPager(binding.viewpager);
        photoAdapter.registerDataSetObserver(binding.circleIndicator.getDataSetObserver());
        autoSlideImages();
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.banner1));
        list.add(new Photo(R.drawable.banner2));
        list.add(new Photo(R.drawable.banner3));
        list.add(new Photo(R.drawable.banner4));
        list.add(new Photo(R.drawable.banner5));
        return list;
    }

    private void autoSlideImages(){
        if (mListPhoto == null || mListPhoto.isEmpty()){
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
                        int currentItem = binding.viewpager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if (currentItem < totalItem){
                            currentItem ++;
                            binding.viewpager.setCurrentItem(currentItem);
                        }else {
                            binding.viewpager.setCurrentItem(0);
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

    @Override
    public void onResume() {
        super.onResume();

    }
}