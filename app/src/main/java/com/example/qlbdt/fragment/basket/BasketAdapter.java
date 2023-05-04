package com.example.qlbdt.fragment.basket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qlbdt.databinding.ItemRcvBasketFragmentBinding;

import java.text.DecimalFormat;
import java.util.List;

public class BasketAdapter  extends RecyclerView.Adapter<BasketAdapter.MyViewHolder>{
    private final Context context;
    private List<Basket> baskets;
    private final HandleBasketClick click;
    private IClickItemBasket iClickItemBasket;

    public BasketAdapter(Context context, HandleBasketClick click, IClickItemBasket iClickItemBasket){
        this.context=context;
        this.click=click;
        this.iClickItemBasket = iClickItemBasket;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setBasketlist(List<Basket> baskets){
        this.baskets=baskets;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvBasketFragmentBinding itemRcvBinding = ItemRcvBasketFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(itemRcvBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(this.baskets.get(position).getBasketimg()).into(holder.binding.imagegiohang);
        holder.binding.imagegiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemBasket.onClickItemBasket(baskets.get(position).getId());
            }
        });
        holder.binding.tenspgh.setText(this.baskets.get(position).getBasketname());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.binding.giagh.setText(decimalFormat.format(this.baskets.get(position).getBasketprice()) + "Đ");
        holder.binding.btnvalue.setText(String.valueOf(this.baskets.get(position).getNumberOrder()));
        int sl = Integer.parseInt(holder.binding.btnvalue.getText().toString());
        if(sl >= 10){
            holder.binding.btntangsp.setVisibility(View.INVISIBLE);
            holder.binding.btgiamsp.setVisibility(View.VISIBLE);
        }else if(sl <=1){
            holder.binding.btgiamsp.setVisibility(View.INVISIBLE);
        }else{
            holder.binding.btntangsp.setVisibility(View.VISIBLE);
            holder.binding.btgiamsp.setVisibility(View.VISIBLE);
        }
        holder.binding.btgiamsp.setOnClickListener(view -> click.btngiamclick(baskets.get(position)));
        holder.binding.btntangsp.setOnClickListener(view -> click.btntangclick(baskets.get(position)));
        holder.binding.btndelete.setOnClickListener(view -> click.btnxoaclick(baskets.get(position)));

    }

    @Override
    public int getItemCount() {
        if(baskets ==null || baskets.size()==0)
            return 0;
        else
            return baskets.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final ItemRcvBasketFragmentBinding binding;
        public MyViewHolder(@NonNull ItemRcvBasketFragmentBinding binding){
            super(binding.getRoot());
            this.binding=binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
    public interface HandleBasketClick{
        void btntangclick(Basket basket);
        void btngiamclick(Basket basket);
        void btnxoaclick(Basket basket);
    }
}
