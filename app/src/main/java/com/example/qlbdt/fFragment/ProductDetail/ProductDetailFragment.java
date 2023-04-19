package com.example.qlbdt.fFragment.ProductDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.qlbdt.R;

/***
 * Hoàng
 * MVVM
 * Thiết kế giao diện
 * */

public class ProductDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }
}