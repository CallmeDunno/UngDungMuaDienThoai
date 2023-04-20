package com.example.qlbdt.fFragment.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qlbdt.databinding.ItemRcvHomeBinding;
import com.example.qlbdt.fInterface.IRecyclerViewOnClick;

import java.util.List;

public class ProductHomeAdapter extends RecyclerView.Adapter<ProductHomeAdapter.ProductHomeViewHolder> {
    private final Context context;
    private final IRecyclerViewOnClick iRecyclerViewOnClick;
    private List<ProductHome> productHomes;

    public ProductHomeAdapter(Context context, IRecyclerViewOnClick iRecyclerViewOnClick) {
        this.context = context;
        this.iRecyclerViewOnClick = iRecyclerViewOnClick;
    }

    public void setData(List<ProductHome> p){
        this.productHomes = p;
    }

    @NonNull
    @Override
    public ProductHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvHomeBinding itemRcvHomeBinding = ItemRcvHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductHomeViewHolder(itemRcvHomeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHomeViewHolder holder, int position) {
        ProductHome productHome = productHomes.get(position);
        if (productHome == null){
            return;
        }
        Glide.with(context).load(productHome.getImage()).into(holder.itemBinding.imgPhone);
        holder.itemBinding.tvPhone.setText(productHome.getName());
        holder.itemBinding.tvPrice.setText(productHome.getPrice());
        holder.itemBinding.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Cần chuyển đến trang chi tiết sản phẩm
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productHomes != null){
            return productHomes.size();
        }
        return 0;
    }

    public static class ProductHomeViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvHomeBinding itemBinding;
        public ProductHomeViewHolder(@NonNull ItemRcvHomeBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}