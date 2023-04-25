package com.example.qlbdt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.fInterface.IRecyclerViewOnClick;
import com.example.qlbdt.object.Smartphone;

import java.util.List;

public class SmartPhoneHomeAdapter extends RecyclerView.Adapter<SmartPhoneHomeAdapter.PhoneViewHolder>{

    private Context mContext;
    private List<Smartphone> mListPhone;
    private final IRecyclerViewOnClick iRecyclerViewOnClick;

    public SmartPhoneHomeAdapter(Context mContext, IRecyclerViewOnClick iRecyclerViewOnClick) {
        this.mContext = mContext;
        this.iRecyclerViewOnClick = iRecyclerViewOnClick;
    }

    public void setData(List<Smartphone> list){
        this.mListPhone = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_home, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        Smartphone phone = mListPhone.get(position);
        if (phone == null){
            return;
        }
        holder.tvPhone.setText(phone.getName());
        holder.tvPrice.setText(phone.getPrice());
        holder.cv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iRecyclerViewOnClick.onClickItemSmartphone(phone);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListPhone != null) {
            return mListPhone.size();
        }
        return 0;
    }


    public static class PhoneViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhone;
        private TextView tvPhone;
        private TextView tvPrice;
        private TextView tvQuantity;
        private CardView cv_item;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_item = itemView.findViewById(R.id.cardview);
            imgPhone = itemView.findViewById(R.id.img_phone);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
