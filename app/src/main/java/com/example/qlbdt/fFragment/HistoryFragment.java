package com.example.qlbdt.fFragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.qlbdt.R;
import com.example.qlbdt.fAdapter.HistoryAdapter;
import com.example.qlbdt.fObject.History;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private ArrayList<History> historyArrayList;
    private HistoryAdapter listAdapter;
    private ListView lstHistory;
    int selectedid=-1;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        historyArrayList = new ArrayList<>();
        lstHistory = view.findViewById(R.id.lvHistoryKien);

        listAdapter = new HistoryAdapter(getActivity(), historyArrayList);
        lstHistory.setAdapter(listAdapter);

        getData();

        lstHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedid = position;
                History history = historyArrayList.get(selectedid);

                // hàm hiển thị dialog
                showDialogHistory(Gravity.CENTER,history );
            }
        });

        return view;
    }

    private void getData(){
        historyArrayList.clear();

    }

    /*
     * Hiển thị dialog ở vị trí giữa màn hình
     * */
    private  void showDialogHistory(int gravity, History history){
//        khởi tạo 1 dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        truyền layout dialog vào tham số
        dialog.setContentView(R.layout.custom_dialog_history);


//        khởi tạo 1 object window
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
//      setLayout
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        có màu trong suốt
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
//        Gán gravity truyền từ tham số
        windowAttributes.gravity = gravity;
//        sau đó set Attributes cho window để hiển thị theo gravity là giữa màn hình
        window.setAttributes(windowAttributes);

        // click ra ngoài thì ẩn dialog
        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }
//        ánh xạ tp bên trong dialog
//        img sản phẩm
        ImageView imgProduct = dialog.findViewById(R.id.imgProductDialog_kien);
        byte[] image = history.getImgSmartPhone();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        imgProduct.setImageBitmap(bitmap);

        //        set tên sản phẩm
        TextView nameProduct = dialog.findViewById(R.id.nameProductDialog_kien);
        nameProduct.setText(history.getNameSmartPhone());


        TextView colorProduct = dialog.findViewById(R.id.colorProductDialog_kien);
        colorProduct.setText("Màu sắc: " + history.getColor());

        TextView brandProduct = dialog.findViewById(R.id.brandProductDialog_kien);
        brandProduct.setText("Hãng: " + history.getBrandName());

        TextView desProduct = dialog.findViewById(R.id.desProductDialog_kien);
        desProduct.setText(history.getDes());

        TextView priceProduct = dialog.findViewById(R.id.priceProductDialog_kien);
        priceProduct.setText(history.getPriceSmartPhone() + " VND");

        TextView orderTime = dialog.findViewById(R.id.orderTimeProductDialog_kien);
        orderTime.setText("Ngày mua: "+history.getOrderTime());

//        avatar user
        ImageView avatarUser = dialog.findViewById(R.id.avatarUserDialog_kien);
        byte[] avatar = history.getAvatar();
        Bitmap bitmapAvatar = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
        avatarUser.setImageBitmap(bitmapAvatar);

        TextView nameUser = dialog.findViewById(R.id.nameUserDialog_kien);
        nameUser.setText("Người mua: " + history.getNameCustomer());

        Button btnCloseDialog = dialog.findViewById(R.id.btnCloseDialogHistory_kien);
        btnCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ẩn dialog
                getData();
                listAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        Button btnRebuy = dialog.findViewById(R.id.btnReBuyDialogHistory_kien);
        btnRebuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        hiển thị dialog
        dialog.show();
    }

}