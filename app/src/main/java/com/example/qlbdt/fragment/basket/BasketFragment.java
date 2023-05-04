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
public class BasketFragment extends Fragment implements BasketAdapter.HandleBasketClick {
    private basketViewModel viewModel;
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
        binding.btnttgiohang.setOnClickListener(view1 -> showDialog());
    }

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setTitle("Notification");
        dialog.setMessage("Are you sure?");
        dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            String userID = SplashScreenActivity.userDatabase.getString("currentUser", "");
            List<Basket> baskets = BasketDatabase.getInstance(requireContext()).basketDao().findBasketWithEmail(userID);
            for (Basket b : baskets) {
                HomeProduct homeProduct = new HomeProduct(b.getId(), b.getBasketName(), b.getPrice(), b.getBasketImg(), b.getBrandName());
                productDetailViewModel.pushHistory(userID, homeProduct, b.getNumberOrder());
                viewModel.DeleteBasket(b);
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
        basketAdapter = new BasketAdapter(getContext(), this, this::handleSelectItem);
        binding.listviewgiohang.setAdapter(basketAdapter);
        List<Basket> baskets = BasketDatabase.getInstance(getActivity()).basketDao().getAllBasket();
        if (baskets.size() == 0) {
            binding.txttongtien.setText("O VND");
        } else {
            basketAdapter.setBasketList(baskets);
            evenUtil(baskets);
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

        viewModel = new ViewModelProvider(this).get(basketViewModel.class);
        viewModel.getListBasketObserver().observe(getViewLifecycleOwner(), baskets -> {

            if (baskets == null) {
                binding.txttongtien.setText("O VND");
            } else {
                basketAdapter.setBasketList(baskets);
                evenUtil(baskets);
            }

        });
    }

    public void evenUtil(List<Basket> baskets) {
        long total = 0;
        for (int i = 0; i < baskets.size(); i++) {
            total += baskets.get(i).getBasketPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        binding.txttongtien.setText(String.format("%sVND", decimalFormat.format(total)));
    }

    @Override
    public void btnTangClick(Basket basket) {

        int sl = basket.getNumberOrder();

        sl++;

        long numberOrder = basket.getNumberOrder();

        long BasketPrince = basket.getBasketPrice();

        basket.setNumberOrder(sl);

        long newBasketPrince = (BasketPrince * sl) / numberOrder;

        basket.setBasketPrice(newBasketPrince);

        viewModel.UpdateBasket(basket);
    }


    @Override
    public void btnLuiClick(Basket basket) {

        int sl = basket.getNumberOrder();

        sl--;

        long numberOrder = basket.getNumberOrder();

        long BasketPrince = basket.getBasketPrice();

        basket.setNumberOrder(sl);

        long newBasketPrince = (BasketPrince * sl) / numberOrder;

        basket.setBasketPrice(newBasketPrince);

        viewModel.UpdateBasket(basket);
    }

    @Override

    public void btnXoaClick(Basket basket) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Basket");
        builder.setMessage("Do you want to delete this basket?");
        builder.setPositiveButton("Yes", (dialog, which) -> viewModel.DeleteBasket(basket));
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
            basketAdapter.setBasketList(baskets);
            evenUtil(baskets);
        }

    }
}
