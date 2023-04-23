package com.example.qlbdt.fFragment.Home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.FragmentHomeBinding;
import com.example.qlbdt.fAdapter.PhotoAdapter;
import com.example.qlbdt.fObject.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Dungx
 */

public class HomeFragment extends Fragment {
    private List<Photo> mListPhoto;
    private Timer mTimer;
    private ProductHomeAdapter smartphoneAdapter;
    private ProductHomeAdapter laptopAdapter;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSlide();
        initUI();
        initViewModel();
    }

    private void initViewModel() {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getListProductHomeLiveData().observe(requireActivity(), productHomes -> {
            List<ProductHome> listSmartphone = new ArrayList<>();
            List<ProductHome> listLaptop = new ArrayList<>();
            for (ProductHome p : productHomes){
                if (p.getType().equals("Smartphone") && listSmartphone.size() < 5){
                    listSmartphone.add(p);
                }
                if (p.getType().equals("Laptop") && listLaptop.size() < 5){
                    listLaptop.add(p);
                }
//                if (listSmartphone.size() == 5 && listLaptop.size() == 5){
//                    break;
//                }
            }
            smartphoneAdapter.setData(listSmartphone);
            laptopAdapter.setData(listLaptop);
        });
    }

    private void initUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rcvPhone.setLayoutManager(linearLayoutManager);
        binding.rcvPc.setLayoutManager(linearLayoutManager2);
        smartphoneAdapter = new ProductHomeAdapter(getActivity(), smartphone -> {
            //TODO: Chuyển hướng đến ProductDetail
        });
        laptopAdapter = new ProductHomeAdapter(requireContext(), smartphone -> {
            //TODO: Chuyển hướng đến ProductDetail
        });
        binding.rcvPhone.setAdapter(smartphoneAdapter);
        binding.rcvPc.setAdapter(laptopAdapter);
        binding.tvSeeMore1.setOnClickListener(view -> {
            //TODO: Chuyển hướng đến trang điện thoại
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_productDetailFragment);
        });

        binding.tvSeeMore2.setOnClickListener(view -> {
            //TODO: Chuyển hướng đến trang Laptop
        });
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

    private void autoSlideImages() {
        if (mListPhoto == null || mListPhoto.isEmpty()) {
            return;
        }

        // Khởi tạo timer
        if (mTimer == null) {
            mTimer = new Timer();
        }

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(() -> {
                    int currentItem = binding.viewpager.getCurrentItem();
                    int totalItem = mListPhoto.size() - 1;
                    if (currentItem < totalItem) {
                        currentItem++;
                        binding.viewpager.setCurrentItem(currentItem);
                    } else {
                        binding.viewpager.setCurrentItem(0);
                    }
                });
            }
        }, 500, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}