package com.example.qlbdt.fragment.product_detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qlbdt.R;
import com.example.qlbdt.fragment.history.History;

import java.util.List;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private Context context;

    public HistoryAdapter(Context context) {
        this.context = context;
    }
    private List<History> lstHistory;

    public void getData(List<History> histories){
        this.lstHistory = histories;
        notifyDataSetChanged();
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
        Glide.with(context).load(history.getImgSmartPhone()).into(holder.imgProduct);
        holder.nameProduct.setText(history.getNameSmartPhone());
        holder.colorProduct.setText("Màu : "+ history.getColor());
        holder.orderTime.setText("Thời gian : " + history.getOrderTime());
        holder.priceProduct.setText(history.getPriceSmartPhone() + " VND");
        holder.numberOrder.setText("x" + history.getNumberOrder());
        holder.brandName.setText("Hãng : " + history.getBrandName());
        holder.btnRepurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(lstHistory!= null) return lstHistory.size();
        return 0;
    }

    public void release(){
        context = null;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView nameProduct, colorProduct, orderTime, priceProduct, numberOrder, brandName;
        private Button btnRepurchase;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            nameProduct = itemView.findViewById(R.id.nameProduct);
            colorProduct = itemView.findViewById(R.id.colorProduct);
            orderTime = itemView.findViewById(R.id.ordertime);
            priceProduct = itemView.findViewById(R.id.priceProduct);
            numberOrder = itemView.findViewById(R.id.numberOrder);
            brandName = itemView.findViewById(R.id.brandName);
            btnRepurchase = itemView.findViewById(R.id.btnRepurchase);
        }
    }
}

