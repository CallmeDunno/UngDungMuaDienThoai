package com.example.qlbdt.fragment.ProductDetail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.qlbdt.databinding.FragmentProductDetailBinding;

/***
 * Dungx
 * MVVM
 * Thiết kế giao diện
 * */

public class ProductDetailFragment extends Fragment {
    private FragmentProductDetailBinding binding;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        binding.btnBuyProductDetail.setOnClickListener(view1 -> {

        });
        binding.btnAddCartProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        initViewModel();

    }

    private void initViewModel() {
        ProductDetailViewModel productDetailViewModel = new ViewModelProvider(requireActivity()).get(ProductDetailViewModel.class);
        String path = ProductDetailFragmentArgs.fromBundle(getArguments()).getDocumentPath();
        productDetailViewModel.setPath(path);
        productDetailViewModel.getProductHomeMutableLiveData().observe(requireActivity(), productHome -> {
            if (productHome.getId() != null){
                progressDialog.dismiss();
                Glide.with(requireActivity()).load(productHome.getImage()).into(binding.imgProductDetail);
                binding.tvNameProductDetail.setText(productHome.getName());
                binding.tvPriceProductDetail.setText(String.format("%s VND", productHome.getPrice()));
                binding.tvDesProductDetail.setText(productHome.getDescription());
                binding.tvBrandProductDetail.setText(productHome.getBrand());
                binding.tvOsProductDetail.setText(productHome.getOS());
                binding.tvCpuProductDetail.setText(productHome.getCpu());
                binding.tvRamProductDetail.setText(productHome.getRam());
                binding.tvRomProductDetail.setText(productHome.getRom());
                binding.tvColorProductDetail.setText(productHome.getColor());
                binding.tvBatteryProductDetail.setText(productHome.getBattery());
                binding.tvWeightProductDetail.setText(productHome.getWeight());
                binding.tvRtProductDetail.setText(productHome.getReleaseTime());
            } else {
                progressDialog.show();
            }
        });
    }

}