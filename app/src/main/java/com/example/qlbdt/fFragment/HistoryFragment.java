package com.example.qlbdt.fFragment;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.qlbdt.fActivity.HomeActivity;
import com.example.qlbdt.fActivity.SmartphoneDetailActivity;
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

        /*
         * Lấy dữ liệu từ trong db
         * */
        getData();

        /*
        * Handle khi click vào 1 item nào đó
        * Sau đó thì bắt được vị trí trong mảng array sau đó lấy thông tin hiển thị lên dialog
        * */
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

    /*
     * Lấy dữ liệu từ trong db
     * */
    private void getData(){
        historyArrayList.clear();
        Cursor c =  HomeActivity.database.SelectData("select History.history_id, History.orderTime, SmartphoneDetail.color, " +
                "Smartphone.name, Smartphone.price, Smartphone.avatar, Brand.name, SmartphoneDetail.description, Person.avatar, Person.name " +
                "from History join Person on Person.person_id = History.person_id join SmartphoneDetail on SmartphoneDetail.smartphone_detail_id = History.smartphone_detail_id " +
                "join Smartphone on Smartphone.smartphone_id = SmartphoneDetail.smartphone_id join Brand on Brand.brand_id = Smartphone.brand_id");
        while(c.moveToNext()){
            int id = c.getInt(0);
            String orderTime = c.getString(1);
            String color = c.getString(2);
            String nameSmartPhone = c.getString(3);
            String price = c.getString(4);
            byte[] imgSmartPhone = c.getBlob(5);
            String BrandName = c.getString(6);
            String desSmartPhone = c.getString(7);
            byte[] avatarUser = c.getBlob(8);
            String nameUser = c.getString(9);

            History history = new History(id,orderTime,color,nameUser,avatarUser,nameSmartPhone,price,imgSmartPhone,BrandName,desSmartPhone);
            historyArrayList.add(history);
        }
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
//                ẩn dialogb
                Intent intent = new Intent(getActivity(), SmartphoneDetailActivity.class);
                intent.putExtra("NameSmartphone", history.getNameSmartPhone());
                startActivity(intent);
            }
        });

//        hiển thị dialog
        dialog.show();
    }

}