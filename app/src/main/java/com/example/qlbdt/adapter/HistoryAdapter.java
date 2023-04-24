package com.example.qlbdt.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.object.History;

import java.util.List;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private List<History> lstHistory;

    public HistoryAdapter(List<History> lstHistory) {
        this.lstHistory = lstHistory;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_history,parent,false);

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History history = lstHistory.get(position);
        if(history== null) return;
        holder.nameProduct.setText(history.getNameSmartPhone());
        holder.colorProduct.setText(history.getColor());
        holder.orderTime.setText(history.getOrderTime());
        holder.priceProduct.setText(history.getPriceSmartPhone() + " VND");
    }

    @Override
    public int getItemCount() {
        if(lstHistory!= null) return lstHistory.size();
        return 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView nameProduct, colorProduct, orderTime, priceProduct;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            nameProduct = itemView.findViewById(R.id.nameProduct);
            colorProduct = itemView.findViewById(R.id.colorProduct);
            orderTime = itemView.findViewById(R.id.ordertime);
            priceProduct = itemView.findViewById(R.id.priceProduct);
        }
    }
}

