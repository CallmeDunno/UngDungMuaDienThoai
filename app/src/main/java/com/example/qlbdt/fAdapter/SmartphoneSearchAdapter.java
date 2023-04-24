package com.example.qlbdt.fAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.fInterface.IRecyclerViewOnClick;
import com.example.qlbdt.fObject.Smartphone;

import java.util.ArrayList;
import java.util.List;

public class SmartphoneSearchAdapter extends RecyclerView.Adapter<SmartphoneSearchAdapter.SmartphoneSearchViewHolder> implements Filterable {

    private Context context;
    private List<Smartphone> smartphones;
    private List<Smartphone> smartphonesOld;
    private IRecyclerViewOnClick iRecyclerViewOnClick;

    public SmartphoneSearchAdapter(Context context, IRecyclerViewOnClick iRecyclerViewOnClick) {
        this.context = context;
        this.iRecyclerViewOnClick = iRecyclerViewOnClick;
    }

    public void setData(List<Smartphone> smartphones){
        this.smartphones = smartphones;
        this.smartphonesOld = smartphones;
        notifyDataSetChanged();
    }

    public void setDataChanged(List<Smartphone> smartphones){
        this.smartphones = smartphones;
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
        holder.tv_name_rcv_fragment_search.setText(smartphone.getName());
        holder.tv_price_rcv_fragment_search.setText("Giá: " + smartphone.getPrice() + " VND");
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String querySearch = charSequence.toString();
                if (querySearch.isEmpty()){
                    smartphones = smartphonesOld;
                } else {
                    List<Smartphone> list = new ArrayList<>();
                    for (Smartphone smp : smartphonesOld){
                        if (smp.getName().toLowerCase().contains(querySearch.toLowerCase())){
                            list.add(smp);
                        }
                    }
                    smartphones = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = smartphones;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                smartphones = (List<Smartphone>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SmartphoneSearchViewHolder extends RecyclerView.ViewHolder {

        ImageView img_avt_rcv_fragment_search;
        TextView tv_name_rcv_fragment_search, tv_price_rcv_fragment_search, tv_quantity_rcv_fragment_search;
        CardView cardView;

        public SmartphoneSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_fragment_search);
            img_avt_rcv_fragment_search = itemView.findViewById(R.id.imagesearch);
            tv_name_rcv_fragment_search = itemView.findViewById(R.id.tv_search_phone);
            tv_price_rcv_fragment_search = itemView.findViewById(R.id.tv_search_price);
        }
    }

}
