package com.example.qlbdt.fragment.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qlbdt.databinding.ItemRcvHomeBinding;

public class HomeProductAdapter extends ListAdapter<HomeProduct, HomeProductAdapter.HomeProductViewHolder> {
    private IClickHomeProduct iClickHomeProduct;

    public HomeProductAdapter() {
        super(HomeProduct.HOME_PRODUCT_DIFF_UTIL);
    }

    @NonNull
    @Override
    public HomeProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvHomeBinding itemRcvHomeBinding = ItemRcvHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HomeProductViewHolder(itemRcvHomeBinding, iClickHomeProduct);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductViewHolder holder, int position) {
        if (getItem(position) == null) {
            return;
        }
        holder.bindingData(getItem(position));
    }

    public void setOnClickItem(IClickHomeProduct iClickHomeProduct) {
        this.iClickHomeProduct = iClickHomeProduct;
    }

    public static class HomeProductViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvHomeBinding itemBinding;
        private HomeProduct homeProduct = null;

        public HomeProductViewHolder(@NonNull ItemRcvHomeBinding itemBinding,
                                     final IClickHomeProduct iClickHomeProduct) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;

            itemBinding.getRoot().setOnClickListener(v -> {
                if (homeProduct != null) {
                    iClickHomeProduct.onClickItem(homeProduct);
                }
            });
        }

        public void bindingData(HomeProduct homeProduct) {
            this.homeProduct = homeProduct;
            Glide.with(itemView.getContext()).load(homeProduct.getImage()).into(itemBinding.imgPhone);
            itemBinding.tvPhone.setText(homeProduct.getName());
            itemBinding.tvPrice.setText(String.format("%s VND", homeProduct.getPrice()));
        }
    }
}
