package com.example.qlbdt.fragment.home;

import android.app.ProgressDialog;
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
import com.example.qlbdt.adapter.PhotoAdapter;
import com.example.qlbdt.databinding.FragmentHomeBinding;
import com.example.qlbdt.object.Photo;

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
    private HomeProductAdapter smartphoneAdapter;
    private HomeProductAdapter laptopAdapter;
    private FragmentHomeBinding binding;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
        initAction();
    }

    private void initAction() {
        binding.tvSeeMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.ActionHomeFragmentToSearchFragment action =
                        HomeFragmentDirections.actionHomeFragmentToSearchFragment();
                action.setTypeProduct("Smartphone");
                Navigation.findNavController(requireView()).navigate(action);
            }
        });

        binding.tvSeeMore2.setOnClickListener(view -> {
            //TODO: Chuyển hướng đến trang Laptop
        });

        smartphoneAdapter.setOnClickItem(this::handleProductSelect);
        laptopAdapter.setOnClickItem(this::handleProductSelect);
    }

    private void handleProductSelect(HomeProduct homeProduct) {
        HomeFragmentDirections.ActionHomeFragmentToProductDetailFragment action =
                HomeFragmentDirections.actionHomeFragmentToProductDetailFragment();
        action.setDocumentPath(homeProduct.getId());
        Navigation.findNavController(requireView()).navigate(action);
    }

    private void initViewModel() {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getListHomeProductLiveData().observe(requireActivity(), productHomes -> {
            if (productHomes.size() == 0) {
                progressDialog.show();
            } else {
                progressDialog.dismiss();
                List<HomeProduct> listSmartphone = new ArrayList<>();
                List<HomeProduct> listLaptop = new ArrayList<>();
                for (HomeProduct p : productHomes) {
                    if (p.getType().equals("Smartphone") && listSmartphone.size() < 5) {
                        listSmartphone.add(p);
                    }
                    if (p.getType().equals("Laptop") && listLaptop.size() < 5) {
                        listLaptop.add(p);
                    }
                    if (listSmartphone.size() == 5 && listLaptop.size() == 5) {
                        break;
                    }
                }
                smartphoneAdapter.submitList(listSmartphone);
                laptopAdapter.submitList(listLaptop);
            }
        });
    }

    private void initView() {
        initSlide();
        initRecycleView();
    }

    private void initRecycleView() {
        progressDialog = new ProgressDialog(requireContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        binding.rcvPc.setLayoutManager(linearLayoutManager2);

        binding.rcvPhone.setLayoutManager(linearLayoutManager);

        smartphoneAdapter = new HomeProductAdapter();
        laptopAdapter = new HomeProductAdapter();

        binding.rcvPhone.setAdapter(smartphoneAdapter);
        binding.rcvPc.setAdapter(laptopAdapter);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

}