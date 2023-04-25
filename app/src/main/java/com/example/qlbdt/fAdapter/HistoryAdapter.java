package com.example.qlbdt.fAdapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.fObject.History;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
        try {
            URL url = new URL(history.getImgSmartPhone());
            InputStream inputStream = url.openConnection().getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.imgProduct.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

