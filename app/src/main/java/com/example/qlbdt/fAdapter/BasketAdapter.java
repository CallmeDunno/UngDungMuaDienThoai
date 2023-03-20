package com.example.qlbdt.fAdapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.fInterface.IRecyclerViewOnClickBasketItem;
import com.example.qlbdt.fObject.Basket;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder> {

    private List<Basket> baskets;
    private IRecyclerViewOnClickBasketItem iRecyclerViewOnClickBasketItem;

    public BasketAdapter(List<Basket> baskets, IRecyclerViewOnClickBasketItem iRecyclerViewOnClickBasketItem) {
        this.baskets = baskets;
        this.iRecyclerViewOnClickBasketItem = iRecyclerViewOnClickBasketItem;
    }

    public void setList(List<Basket> b){
        this.baskets = b;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_basket_fragment, parent, false);
        return new BasketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {
        Basket basket = baskets.get(position);
        if (basket == null){
            return;
        }
        byte[] image = basket.getImageSmp();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.img_avt_rcv_basket.setImageBitmap(bitmap);
        holder.tv_brand_name_basket.setText("Hãng: " + basket.getNameBrand());
        holder.tv_smp_name_basket.setText(basket.getNameSmp());
        holder.tv_price_basket.setText("Giá: " + basket.getPriceBasket() + " VND");
        holder.cv_fragment_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iRecyclerViewOnClickBasketItem.onClickItemBasket(basket, basket.getIdBasket());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (baskets != null){
            return baskets.size();
        }
        return 0;
    }

    public class BasketViewHolder extends RecyclerView.ViewHolder {

        TextView tv_brand_name_basket, tv_smp_name_basket, tv_price_basket;
        ImageView img_avt_rcv_basket;
        CardView cv_fragment_basket;
        public BasketViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_brand_name_basket = itemView.findViewById(R.id.tv_brand_name_basket);
            tv_smp_name_basket = itemView.findViewById(R.id.tv_smp_name_basket);
            tv_price_basket = itemView.findViewById(R.id.tv_price_basket);
            img_avt_rcv_basket = itemView.findViewById(R.id.img_avt_rcv_basket);
            cv_fragment_basket = itemView.findViewById(R.id.cv_fragment_basket);
        }
    }

}
