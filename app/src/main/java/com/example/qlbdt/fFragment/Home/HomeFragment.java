package com.example.qlbdt.fFragment.Home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.FragmentHomeBinding;
import com.example.qlbdt.fAdapter.PhotoAdapter;
import com.example.qlbdt.fInterface.IRecyclerViewOnClick;
import com.example.qlbdt.fObject.Photo;
import com.example.qlbdt.fObject.Smartphone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

        /**
         * View Model
         * Đang lỗi
         * */

//        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//        homeViewModel.getListProductHomeLiveData().observe(getActivity(), new Observer<List<ProductHome>>() {
//            @Override
//            public void onChanged(List<ProductHome> productHomes) {
//                productHomeAdapter.setData(productHomes);
//            }
//        });

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
        productHomeAdapter.setData(getListPhone());
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

    private List<ProductHome> getListPhone(){
        List<ProductHome> list = new ArrayList<>();
        String url = "https://firebasestorage.googleapis.com/v0/b/productmanager-ttcm-01.appspot.com/o/apple_iphone_13_pro_max.png?alt=media&token=609e647f-f001-4531-88d3-3e35f8c23b62";
        list.add(new ProductHome("iPhone 13 Pro Max", "23.323.000", url));
        list.add(new ProductHome("Samsung Galaxy S23 Ultra", "23.323.000", url));
        list.add(new ProductHome("a", "23.323.000", url));
        list.add(new ProductHome("a", "23.323.000", url));

        /**
         * Kết nối với firebase
         * Đang lỗi
         * */

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                Log.d("HomeFragment", doc.getId() + " => " + doc.getData());
                            }
                        } else {
                            Log.e("HomeFragment", "connect to firebase fail");
                        }
                    }
                });

        return list;
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