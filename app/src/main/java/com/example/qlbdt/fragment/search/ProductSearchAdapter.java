package com.example.qlbdt.fragment.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qlbdt.databinding.ItemRcvSearchFragmentBinding;

import java.util.Comparator;
import java.util.List;

public class ProductSearchAdapter extends RecyclerView.Adapter<ProductSearchAdapter.ProductSearchViewHolder> {
    private final Context context;
    private final IRecyclerViewOnClick iRecyclerViewOnClick;
    private List<ProductSearch> productSearches;

    public ProductSearchAdapter(Context context, IRecyclerViewOnClick iRecyclerViewOnClick1) {
        this.context = context;
        this.iRecyclerViewOnClick = iRecyclerViewOnClick1;
    }

    public void setData(List<ProductSearch> p) {
        this.productSearches = p;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvSearchFragmentBinding itemRcvSearchFragmentBinding = ItemRcvSearchFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductSearchViewHolder(itemRcvSearchFragmentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSearchViewHolder holder, int position) {
        ProductSearch productSearch = productSearches.get(position);
        if (productSearch == null) {
            return;
        }
        Glide.with(context).load(productSearch.getImage()).into(holder.itemBinding.imagesearch);
        holder.itemBinding.tvSearchPhone.setText(productSearch.getName());
        holder.itemBinding.tvSearchPrice.setText(String.format("%s VND", productSearch.getPrice()));
        holder.itemBinding.cvFragmentSearch.setOnClickListener(view -> iRecyclerViewOnClick.onClickItem(productSearch));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSearchViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        if (productSearches != null) {
            return productSearches.size();
        }
        return 0;
    }

    public static class ProductSearchViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvSearchFragmentBinding itemBinding;

        public ProductSearchViewHolder(@NonNull ItemRcvSearchFragmentBinding itemBinding) {
            super(itemBinding.getRoot());

            this.itemBinding = itemBinding;
        }
    }

    public static class ProductSearchComparator implements Comparator<ProductSearch> {

        @Override
        public int compare(ProductSearch p1, ProductSearch p2) {
            // So sánh theo tên sản phẩm và trả về kết quả
            return p1.getName().compareTo(p2.getName());
        }
    }
}
