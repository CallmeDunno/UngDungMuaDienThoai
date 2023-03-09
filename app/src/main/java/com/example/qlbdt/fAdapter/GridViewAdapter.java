package com.example.qlbdt.fAdapter;

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
import com.example.qlbdt.fObject.Smartphone;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Smartphone> smartphoneList;

    public GridViewAdapter(Context context, int layout, List<Smartphone> smartphoneList) {
        this.context = context;
        this.layout = layout;
        this.smartphoneList = smartphoneList;
    }

    @Override
    public int getCount() {
        return smartphoneList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view  = layoutInflater.inflate(layout, null);

            viewHolder.img_cus_gv = view.findViewById(R.id.img_cus_gv);
            viewHolder.tv_name_cus_gv = view.findViewById(R.id.tv_name_cus_gv);
            viewHolder.tv_price_cus_gv = view.findViewById(R.id.tv_price_cus_gv);
            viewHolder.tv_quantity_cus_gv = view.findViewById(R.id.tv_quantity_cus_gv);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        byte[] image = smartphoneList.get(i).getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        viewHolder.img_cus_gv.setImageBitmap(bitmap);
        viewHolder.tv_name_cus_gv.setText(smartphoneList.get(i).getName());
        viewHolder.tv_price_cus_gv.setText(smartphoneList.get(i).getPrice() + " VND");
        viewHolder.tv_quantity_cus_gv.setText(String.valueOf(smartphoneList.get(i).getQuantity()));

        return view;
    }

    private class ViewHolder{
        ImageView img_cus_gv;
        TextView tv_name_cus_gv, tv_price_cus_gv, tv_quantity_cus_gv;
    }
}
