package com.example.qlbdt.fAdapter;

import android.content.Context;
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
import com.example.qlbdt.fInterface.IRecyclerViewOnClick;
import com.example.qlbdt.fObject.Smartphone;

import java.util.List;

public class SmartphoneSearchAdapter extends RecyclerView.Adapter<SmartphoneSearchAdapter.SmartphoneSearchViewHolder> {

    private Context context;
    private List<Smartphone> smartphones;
    private IRecyclerViewOnClick iRecyclerViewOnClick;

    public SmartphoneSearchAdapter(Context context, List<Smartphone> smartphones, IRecyclerViewOnClick iRecyclerViewOnClick) {
        this.context = context;
        this.smartphones = smartphones;
        this.iRecyclerViewOnClick = iRecyclerViewOnClick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SmartphoneSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_search_fragment, parent, false);
        return new SmartphoneSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmartphoneSearchViewHolder holder, int position) {
        Smartphone smartphone = smartphones.get(position);
        if (smartphone == null){
            return;
        }
        byte[] image = smartphone.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.img_avt_rcv_fragment_search.setImageBitmap(bitmap);
        holder.tv_name_rcv_fragment_search.setText(smartphone.getName());
        holder.tv_price_rcv_fragment_search.setText("Giá: " + smartphone.getPrice());
        holder.tv_quantity_rcv_fragment_search.setText("Số lượng: " + smartphone.getQuantity());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iRecyclerViewOnClick.onClickItemSmartphone(smartphone);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (smartphones != null){
            return smartphones.size();
        }
        return 0;
    }

    public class SmartphoneSearchViewHolder extends RecyclerView.ViewHolder {

        ImageView img_avt_rcv_fragment_search;
        TextView tv_name_rcv_fragment_search, tv_price_rcv_fragment_search, tv_quantity_rcv_fragment_search;
        CardView cardView;

        public SmartphoneSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_fragment_search);
            img_avt_rcv_fragment_search = itemView.findViewById(R.id.img_avt_rcv_fragment_search);
            tv_name_rcv_fragment_search = itemView.findViewById(R.id.tv_name_rcv_fragment_search);
            tv_price_rcv_fragment_search = itemView.findViewById(R.id.tv_price_rcv_fragment_search);
            tv_quantity_rcv_fragment_search = itemView.findViewById(R.id.tv_quantity_rcv_fragment_search);
        }
    }

}
