package com.example.qlbdt.fAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlbdt.R;
import com.example.qlbdt.fObject.History;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
   // ngữ cảnh của ứng dụng
    private Activity context;
    //    danh sách các contact
    private ArrayList<History> histories;

    //    object để phần tích layout
    private LayoutInflater inflater;

    public HistoryAdapter(Activity activity, ArrayList<History> histories) {
        this.context = activity;
        this.histories = histories;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return histories.size();
    }

    @Override
    public Object getItem(int i) {
        return histories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return histories.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        if(v==null){
            v = inflater.inflate(R.layout.item_lv_history_kien, null);
            //        set ảnh cho sản phẩm
            ImageView imgProduct = v.findViewById(R.id.imgProduct_kien);
            byte[] image = histories.get(i).getImgSmartPhone();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            imgProduct.setImageBitmap(bitmap);


            //        set tên sản phẩm
            TextView nameProduct = v.findViewById(R.id.nameProduct_kien);
            nameProduct.setText(histories.get(i).getNameSmartPhone());


            TextView colorProduct = v.findViewById(R.id.colorProduct_kien);
            colorProduct.setText(histories.get(i).getColor());

            TextView priceProduct = v.findViewById(R.id.priceProduct_kien);
            priceProduct.setText(histories.get(i).getPriceSmartPhone());

            TextView orderTime = v.findViewById(R.id.ordertime_kien);
            orderTime.setText(histories.get(i).getOrderTime());
        }
        return v;
    }
}
