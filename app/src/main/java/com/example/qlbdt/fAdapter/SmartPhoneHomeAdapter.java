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
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.fFragment.HomeFragment;
import com.example.qlbdt.fObject.Phone;


import java.util.List;

public class SmartPhoneHomeAdapter extends RecyclerView.Adapter<SmartPhoneHomeAdapter.PhoneViewHolder> {

    private Context mContext;
    private List<Phone> mListPhone;

    public SmartPhoneHomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Phone> list){
        this.mListPhone = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_home_tuan, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        Phone phone = mListPhone.get(position);
        if (phone == null){
            return;
        }
        byte[] image = phone.getResouceImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imgPhone.setImageBitmap(bitmap);
        holder.tvPhone.setText(phone.getName());
        holder.tvPrice.setText("Giá: " + phone.getPrice());
        holder.tvQuantity.setText("Số lượng: "+phone.getQuantity());
    }

    @Override
    public int getItemCount() {
        if (mListPhone != null) {
            return mListPhone.size();
        }
        return 0;
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhone;
        private TextView tvPhone;
        private TextView tvPrice;
        private TextView tvQuantity;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhone = itemView.findViewById(R.id.img_phone);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
}
