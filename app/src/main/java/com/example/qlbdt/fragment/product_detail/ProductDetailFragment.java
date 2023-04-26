package com.example.qlbdt.fragment.product_detail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

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

    private Context context;

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
        binding.btnBuyProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        binding.btnAddCartProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        initViewModel();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    private void initViewModel() {
        ProductDetailViewModel productDetailViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ProductDetailViewModel.class);
        String path = ProductDetailFragmentArgs.fromBundle(getArguments()).getDocumentPath();
        productDetailViewModel.setPath(path);
        productDetailViewModel.getProductHomeMutableLiveData().observe((LifecycleOwner) context, productHome -> {
            if (productHome.getId() != null){
                Glide.with(context).load(productHome.getImage()).into(binding.imgProductDetail);
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
                progressDialog.dismiss();
            } else {
                progressDialog.show();
            }
        });
    }

}