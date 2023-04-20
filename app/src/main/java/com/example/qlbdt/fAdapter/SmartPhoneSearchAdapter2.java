package com.example.qlbdt.fAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qlbdt.R;
import com.example.qlbdt.fFragment.Search.SearchFragment;
import com.example.qlbdt.fObject.User2;

import java.util.ArrayList;

public class SmartPhoneSearchAdapter2 extends  RecyclerView.Adapter<SmartPhoneSearchAdapter2.MyViewHolder> {
    Context context;
    ArrayList<User2>  user2ArrayList;

    public SmartPhoneSearchAdapter2(SearchFragment context, ArrayList<User2> user2ArrayList) {
    }

    public SmartPhoneSearchAdapter2(ArrayList<User2> user2ArrayList) {
        this.context = context;
        this.user2ArrayList = user2ArrayList;
    }

    @NonNull
    @Override
    public SmartPhoneSearchAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_search_fragment,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmartPhoneSearchAdapter2.MyViewHolder holder, int position) {
    User2 user2 = user2ArrayList.get(position);
    holder.tv_name_rcv_fragment_search.setText(user2.getImage());
    holder.tv_price_rcv_fragment_search.setText(user2.getPrice());
        Glide.with(holder.img_avt_rcv_fragment_search.getContext())
                .load(user2.getImage())
                .into(holder.img_avt_rcv_fragment_search);
    }

    @Override
    public int getItemCount() {
        return user2ArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img_avt_rcv_fragment_search;
        TextView tv_name_rcv_fragment_search, tv_price_rcv_fragment_search;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avt_rcv_fragment_search = itemView.findViewById(R.id.img_avt_rcv_fragment_search);
            tv_name_rcv_fragment_search = itemView.findViewById(R.id.tv_name_rcv_fragment_search);
            tv_price_rcv_fragment_search = itemView.findViewById(R.id.tv_price_rcv_fragment_search);
        }
    }
}

