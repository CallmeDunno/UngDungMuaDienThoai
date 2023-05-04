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
    private final IClickItemBasket iClickItemBasket;

    public BasketAdapter(Context context, HandleBasketClick click, IClickItemBasket iClickItemBasket){
        this.context=context;
        this.click=click;
        this.iClickItemBasket = iClickItemBasket;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setBasketList(List<Basket> baskets){
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
        Glide.with(context).load(this.baskets.get(position).getBasketImg()).into(holder.binding.imagegiohang);
        holder.binding.imagegiohang.setOnClickListener(view -> iClickItemBasket.onClickItemBasket(baskets.get(position).getId()));
        holder.binding.tenspgh.setText(this.baskets.get(position).getBasketName());
        holder.binding.tvBrandCart.setText(this.baskets.get(position).getBrandName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.binding.giagh.setText(decimalFormat.format(this.baskets.get(position).getBasketPrice()) + "Đ");
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
        holder.binding.btgiamsp.setOnClickListener(view -> click.btnLuiClick(baskets.get(position)));
        holder.binding.btntangsp.setOnClickListener(view -> click.btnTangClick(baskets.get(position)));
        holder.binding.btndelete.setOnClickListener(view -> click.btnXoaClick(baskets.get(position)));

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
            binding.getRoot().setOnClickListener(view -> {

            });
        }
    }
    public interface HandleBasketClick{
        void btnTangClick(Basket basket);
        void btnLuiClick(Basket basket);
        void btnXoaClick(Basket basket);
    }
}
