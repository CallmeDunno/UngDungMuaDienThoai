package com.example.qlbdt.fragment.product_detail;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bumptech.glide.Glide;
import com.example.qlbdt.R;
import com.example.qlbdt.activity.SplashScreenActivity;
import com.example.qlbdt.databinding.FragmentProductDetailBinding;
import com.example.qlbdt.fragment.home.HomeProduct;

import java.text.MessageFormat;

/***
 * Dungx
 * MVVM
 * Thiết kế giao diện
 * */

public class ProductDetailFragment extends Fragment {
    private FragmentProductDetailBinding binding;
    private ProgressDialog progressDialog;
    private ProductDetailViewModel productDetailViewModel;
    private HomeProduct homeProduct = null;

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
        initData(getArguments());
        initAction();
    }

    private void initData(Bundle arguments) {
        initViewModel();
        String path = ProductDetailFragmentArgs.fromBundle(arguments).getDocumentPath();
        productDetailViewModel.setDocumentPath(path);
    }

    private void initAction() {
        binding.btnBuyProductDetail.setOnClickListener(this::handleButtonBuy);
        binding.btnAddCartProductDetail.setOnClickListener(this::handleButtonAddToCart);
    }

    private void initViewModel() {
        productDetailViewModel = new ViewModelProvider((ViewModelStoreOwner) requireContext()).get(ProductDetailViewModel.class);
        productDetailViewModel.getProductHomeMutableLiveData().observe(getViewLifecycleOwner(), homeProduct -> {
            if (homeProduct.getId() != null) {
                this.homeProduct = homeProduct;
                Glide.with(requireContext()).load(homeProduct.getImage()).into(binding.imgProductDetail);
                binding.tvNameProductDetail.setText(homeProduct.getName());
                binding.tvPriceProductDetail.setText(String.format("%s VND", homeProduct.getPrice()));
                binding.tvDesProductDetail.setText(homeProduct.getDescription());
                binding.tvBrandProductDetail.setText(homeProduct.getBrand());
                binding.tvOsProductDetail.setText(homeProduct.getOS());
                binding.tvCpuProductDetail.setText(homeProduct.getCpu());
                binding.tvRamProductDetail.setText(homeProduct.getRam());
                binding.tvRomProductDetail.setText(homeProduct.getRom());
                binding.tvColorProductDetail.setText(homeProduct.getColor());
                binding.tvBatteryProductDetail.setText(homeProduct.getBattery());
                binding.tvWeightProductDetail.setText(homeProduct.getWeight());
                binding.tvRtProductDetail.setText(homeProduct.getReleaseTime());
                progressDialog.dismiss();
            } else {
                progressDialog.show();
            }
        });
    }

    private void handleButtonAddToCart(View view) {
        showDialogAddToCart();
    }

    private void handleButtonBuy(View view) {
        showDialogBuy();
    }

    private void showDialogAddToCart() {

    }

    private void showDialogBuy() {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_buy_pd);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_yes = dialog.findViewById(R.id.btn_yes);
        Button btn_no = dialog.findViewById(R.id.btn_no);
        ImageView img_minus = dialog.findViewById(R.id.img_minus);
        ImageView img_plus = dialog.findViewById(R.id.img_plus);
        TextView tv_quantity = dialog.findViewById(R.id.tv_quantity);

        img_minus.setOnClickListener(view -> {
            int quantity = Integer.parseInt(tv_quantity.getText().toString().trim());
            if (quantity > 1){
                quantity--;
                tv_quantity.setText(MessageFormat.format("{0}", quantity));
            }
        });

        img_plus.setOnClickListener(view -> {
            int quantity = Integer.parseInt(tv_quantity.getText().toString().trim());
            quantity++;
            tv_quantity.setText(MessageFormat.format("{0}", quantity));
        });

        btn_yes.setOnClickListener(view -> {
            String userID = SplashScreenActivity.userDatabase.getString("username", "");
            int quantity = Integer.parseInt(tv_quantity.getText().toString().trim());
            String status = productDetailViewModel.pushHistory(userID, homeProduct, quantity);
            dialog.dismiss();
        });

        btn_no.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }
}