package com.example.qlbdt.fFragment.Basket;

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

import java.text.DecimalFormat;
import java.util.List;

public class BasketAdapter  extends RecyclerView.Adapter<BasketAdapter.MyViewHolder>{
    private Context context;
    private List<Basket> baskets;
    private HandleBasketClick click;
    public BasketAdapter(Context context, HandleBasketClick click){
        this.context=context;
        this.click=click;
    }
    public void setBasketlist(List<Basket> baskets){
        this.baskets=baskets;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rcv_basket_fragment,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tensp.setText(this.baskets.get(position).getBasketName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giasp.setText(decimalFormat.format(this.baskets.get(position).getBasketValue()) + "Đ");
        holder.btnvalue.setText(String.valueOf(this.baskets.get(position).getBasketQuantity()));
        int sl = Integer.parseInt(holder.btnvalue.getText().toString());
        if(sl >= 10){
            holder.btntang.setVisibility(View.INVISIBLE);
            holder.btngiam.setVisibility(View.VISIBLE);
        }else if(sl <=1){
            holder.btngiam.setVisibility(View.INVISIBLE);
        }else{
            holder.btntang.setVisibility(View.VISIBLE);
            holder.btngiam.setVisibility(View.VISIBLE);
        }
        holder.btngiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.btngiamclick(baskets.get(position));
            }
        });
        holder.btntang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.btntangclick(baskets.get(position));
            }
        });
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
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
        ImageView giohang;
        TextView tensp;
        TextView giasp;
        Button btngiam;
        Button btntang;
        Button btnvalue;
        Button btnxoa;
        public MyViewHolder(View view){
            super(view);
            giohang=view.findViewById(R.id.imagegiohang);
            tensp=view.findViewById(R.id.tenspgh);
            giasp=view.findViewById(R.id.giagh);
            btngiam=view.findViewById(R.id.btgiamsp);
            btntang=view.findViewById(R.id.btntangsp);
            btnvalue=view.findViewById(R.id.btnvalue);
            btnxoa=view.findViewById(R.id.btndelete);
        }
    }
    public interface HandleBasketClick{
        void btntangclick(Basket basket);
        void btngiamclick(Basket basket);
        void btnxoaclick(Basket basket);
    }
}
