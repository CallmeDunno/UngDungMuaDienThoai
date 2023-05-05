package com.example.qlbdt.fragment.history;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qlbdt.databinding.ItemRcvHistoryBinding;

public class HistoryAdapter extends ListAdapter<History, HistoryAdapter.HistoryViewHolder> {
    private IClickButtonHistory iClickButtonHistory;

    public HistoryAdapter() {
        super(History.HISTORY_DIFF_UTIL);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvHistoryBinding binding = ItemRcvHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        if (getItem(position) == null) {
            return;
        }
        holder.bindData(getItem(position), iClickButtonHistory);
    }

    public void setClickButtonHistory(IClickButtonHistory iClickButtonHistory) {
        this.iClickButtonHistory = iClickButtonHistory;
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvHistoryBinding itemBinding;

        public HistoryViewHolder(@NonNull ItemRcvHistoryBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bindData(History history, IClickButtonHistory iClickButtonHistory) {
            Glide.with(itemView.getContext()).load(history.getImage()).into(itemBinding.imgHistory);
            itemBinding.tvBrandHistory.setText(history.getBrand());
            itemBinding.tvNameHistory.setText(history.getName());
            itemBinding.tvPriceHistory.setText(String.format("Price: %s VND", history.getPrice()));
            itemBinding.tvQuantityHistory.setText(String.format("Quantity: %s", history.getQuantity()));
            itemBinding.tvTotalHistory.setText(String.format("Total: %s VND", history.getTotalMoney()));
            itemBinding.tvTimeToBuyHistory.setText(String.format("Time to buy: %s", history.getTimeToBuy()));
            itemBinding.btnRepurchase.setOnClickListener(view -> iClickButtonHistory.onClickRepurchaseButton(history.getProductID()));
        }
    }
}

