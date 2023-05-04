package com.example.qlbdt.fragment.basket;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.qlbdt.activity.SplashScreenActivity;
import com.example.qlbdt.databinding.FragmentBasketBinding;
import com.example.qlbdt.fragment.home.HomeProduct;
import com.example.qlbdt.fragment.product_detail.ProductDetailViewModel;
import com.example.qlbdt.other.FCM;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Tuấn
 * Room + MVVM
 * Thiết kế giao diện
 */

public class BasketFragment extends Fragment implements BasketAdapter.HandleBasketClick {
    private BasketViewmodel viewmodel;
    private ProductDetailViewModel productDetailViewModel;
    private BasketAdapter basketAdapter;
    private FragmentBasketBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBasketBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecycleView();
        initAction();

    }

    private void initAction() {
        binding.btnttgiohang.setOnClickListener(view1 -> {
            showDialog();
        });
    }

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setTitle("Notification");
        dialog.setMessage("Are you sure?");
        dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            String userID = SplashScreenActivity.userDatabase.getString("currentUser", "");
            List<Basket> baskets = BasketDatabase.getInstance(requireContext()).basketDao().findBasketWithEmail(userID);
            for (Basket b : baskets) {
                HomeProduct homeProduct = new HomeProduct(b.getId(), b.getBasketname(), b.getPrice(), b.getBasketimg(), b.getBasketbrandName());
                productDetailViewModel.pushHistory(userID, homeProduct, b.getNumberOrder());
                viewmodel.Deletebasket(b);
            }
            Toast.makeText(requireContext(), "Buy successfully", Toast.LENGTH_SHORT).show();
            FCM.FCM(requireContext());
        });
        dialog.setNegativeButton("No", (dialogInterface, i) -> {

        });
        dialog.show();
    }


    @SuppressLint("SetTextI18n")
    private void initRecycleView() {
        binding.listviewgiohang.setLayoutManager(new LinearLayoutManager(getActivity()));
        basketAdapter = new BasketAdapter(getContext(), this, new IClickItemBasket() {
            @Override
            public void onClickItemBasket(String id) {
                handleSelectItem(id);
            }
        });
        binding.listviewgiohang.setAdapter(basketAdapter);
        List<Basket> baskets = BasketDatabase.getInstance(getActivity()).basketDao().getAllBasket();
        if (baskets.size() == 0) {
            binding.txttongtien.setText("O VND");
        } else {
            basketAdapter.setBasketlist(baskets);
            ;
            EvenUltil(baskets);
        }

    }

    private void handleSelectItem(String id) {
        BasketFragmentDirections.ActionBasketFragmentToProductDetailFragment action =
                BasketFragmentDirections.actionBasketFragmentToProductDetailFragment();
        action.setDocumentPath(id);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @SuppressLint("SetTextI18n")
    private void initViewModel() {
        productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        viewmodel = new ViewModelProvider(this).get(BasketViewmodel.class);
        viewmodel.getListofbasketobserver().observe(getViewLifecycleOwner(), baskets -> {

            if (baskets == null) {
                binding.txttongtien.setText("O VND");
            } else {
                basketAdapter.setBasketlist(baskets);
                EvenUltil(baskets);
            }

        });
    }

    public void EvenUltil(List<Basket> baskets) {
        long tongtien = 0;
        for (int i = 0; i < baskets.size(); i++) {
            tongtien += baskets.get(i).getBasketprice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.txttongtien.setText(String.format("%sVND", decimalFormat.format(tongtien)));
    }

    @Override
    public void btntangclick(Basket basket) {

        int sl = basket.getNumberOrder();

        sl++;

        long slht = basket.getNumberOrder();

        long giaht = basket.getBasketprice();

        basket.setNumberOrder(sl);

        long giamoi = (giaht * sl) / slht;

        basket.setBasketprice(giamoi);

        viewmodel.Updatetbasket(basket);
    }


    @Override
    public void btngiamclick(Basket basket) {

        int sl = basket.getNumberOrder();

        sl--;

        long slht = basket.getNumberOrder();

        long giaht = basket.getBasketprice();

        basket.setNumberOrder(sl);

        long giamoi = (giaht * sl) / slht;

        basket.setBasketprice(giamoi);

        viewmodel.Updatetbasket(basket);
    }

    @Override

    public void btnxoaclick(Basket basket) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Basket");
        builder.setMessage("Do you want to deleete this basket?");
        builder.setPositiveButton("Yes", (dialog, which) -> viewmodel.Deletebasket(basket));
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        List<Basket> baskets = BasketDatabase.getInstance(getActivity()).basketDao().getAllBasket();
        if (baskets.size() == 0) {
            binding.txttongtien.setText("O VND");
        } else {
            basketAdapter.setBasketlist(baskets);
            EvenUltil(baskets);
        }

    }
}
