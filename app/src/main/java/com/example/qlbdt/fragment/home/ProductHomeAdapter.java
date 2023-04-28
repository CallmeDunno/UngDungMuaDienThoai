package com.example.qlbdt.fragment.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qlbdt.databinding.ItemRcvHomeBinding;

public class ProductHomeAdapter extends ListAdapter<ProductHome, ProductHomeAdapter.ProductHomeViewHolder> {

    private IClickHomeProduct iClickHomeProduct;

    public ProductHomeAdapter() {
        super(ProductHome.PRODUCT_HOME_DIFF_UTIL);
    }

    @NonNull
    @Override
    public ProductHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvHomeBinding itemRcvHomeBinding = ItemRcvHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductHomeViewHolder(itemRcvHomeBinding, iClickHomeProduct);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHomeViewHolder holder, int position) {
        if (getItem(position) == null) {
            return;
        }

        holder.bindingData(getItem(position));
        Log.e("PhongPN","onBindViewHolder: " + getItem(position).getId() + " " + getItem(position).getName());

    }

    public void setOnClickItem(IClickHomeProduct iClickHomeProduct) {
        this.iClickHomeProduct = iClickHomeProduct;
    }


    public static class ProductHomeViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvHomeBinding itemBinding;

        private ProductHome productHome = null;

        public ProductHomeViewHolder(@NonNull ItemRcvHomeBinding itemBinding,
                                     final IClickHomeProduct iClickHomeProduct) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;

            itemBinding.getRoot().setOnClickListener(v -> {
                if (productHome != null) {
                    iClickHomeProduct.onClickItem(productHome);
                }
            });
        }

        public void bindingData(ProductHome productHome) {
            Log.i("PhongPN","Bind Data: " + productHome.getId() + " " + productHome.getName());
            this.productHome = productHome;

            Glide.with(itemView.getContext()).load(productHome.getImage()).into(itemBinding.imgPhone);
            itemBinding.tvPhone.setText(productHome.getName());
            itemBinding.tvPrice.setText(String.format("%s VND", productHome.getPrice()));
        }
    }
}
