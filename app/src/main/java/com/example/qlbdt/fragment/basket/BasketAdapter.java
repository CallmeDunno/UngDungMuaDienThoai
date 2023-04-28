package com.example.qlbdt.fragment.basket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.FragmentBasketBinding;
import com.example.qlbdt.databinding.ItemRcvBasketFragmentBinding;
import com.example.qlbdt.databinding.ItemRcvHomeBinding;
import com.example.qlbdt.fFragment.Home.ProductHomeAdapter;

import java.text.DecimalFormat;
import java.util.List;

public class BasketAdapter  extends RecyclerView.Adapter<BasketAdapter.MyViewHolder>{
    //private final Context context;
    private List<Basket> baskets;
    private final HandleBasketClick click;

    public BasketAdapter( HandleBasketClick click){
       // this.context=context;
        this.click=click;
    }
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

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tenspgh.setText(this.baskets.get(position).getBasketName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.binding.giagh.setText(decimalFormat.format(this.baskets.get(position).getBasketValue()) + "Đ");
        holder.binding.btnvalue.setText(String.valueOf(this.baskets.get(position).getBasketQuantity()));
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
        holder.binding.btgiamsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.btngiamclick(baskets.get(position));
            }
        });
        holder.binding.btntangsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.btntangclick(baskets.get(position));
            }
        });
        holder.binding.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.btnxoaclick(baskets.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        if(baskets ==null || baskets.size()==0)
            return 0;
        else
            return baskets.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemRcvBasketFragmentBinding binding;
        public MyViewHolder(@NonNull ItemRcvBasketFragmentBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    public interface HandleBasketClick{
        void btntangclick(Basket basket);
        void btngiamclick(Basket basket);
        void btnxoaclick(Basket basket);
    }
}
