package com.example.qlbdt.fFragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.qlbdt.R;
import com.example.qlbdt.fActivity.EditActivity;
import com.example.qlbdt.fActivity.HomeActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    private CircleImageView avatar;
    private TextView name, phoneNum, address, email;
    private Button btn_edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        avatar = view.findViewById(R.id.avatar_QA);
        name = view.findViewById(R.id.name_QA);
        phoneNum = view.findViewById(R.id.phone_QA);
        address = view.findViewById(R.id.address_QA);
        email = view.findViewById(R.id.email_QA);
        btn_edit = view.findViewById(R.id.btn_edit_QA);

        setData();

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditActivity.class));
            }
        });

        return view;
    }

    private void setData(){
        //chuỗi SQL query để truy vấn thông tin được lưu trữ trong cơ sở dữ liệu
        String accountquery = "SELECT name, phone, address, email, avatar FROM Person";
        //thực hiện truy vấn SQL của chuỗi accountquery, trả về đối tượng chứa kết quả của truy vấn được trả về từ csdl
        Cursor c = HomeActivity.database.SelectData(accountquery);
        //di chuyển con trỏ của đối tượng Cursor đến bản ghi đầu tiên trong kết quả truy vấn
        c.moveToFirst();

        String ten = c.getString(0);
        String sdt = c.getString(1);
        String dc = c.getString(2);
        String mail = c.getString(3);
        byte[] image = c.getBlob(4);
        //chuyển đổi mảng byte chứa dữ liệu hình ảnh thành đối tượng Bitmap để có thể hiển thị hình ảnh
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        //hàm decodeByteArray(mảng byte chứa dữ liệu, vị trí bắt đầu đọc dữ liệu, độ dài của mảng)
        // được sử dụng để giải mã dữ liệu hình ảnh từ mảng byte

        avatar.setImageBitmap(bitmap);
        name.setText(ten);
        phoneNum.setText(sdt);
        address.setText(dc);
        email.setText(mail);
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
}