package com.example.qlbdt.fFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.qlbdt.R;
import com.example.qlbdt.fAdapter.SupportAdapter;
import com.example.qlbdt.fObject.Support;

import java.util.ArrayList;

public class SupportFragment extends Fragment {
    //chúng ta khai báo ba biến: listViewSupport, lstSupport và supportAdapter
    //listViewSupport là một tham chiếu đến một đối tượng ListView, sẽ hiển thị danh sách các thành viên hỗ trợ.
    // lstSupport là một đối tượng ArrayList, chứa danh sách các đối tượng Support.
    // supportAdapter là một phiên bản của lớp SupportAdapter, sẽ được sử dụng để gắn dữ liệu Support vào ListView.
    ListView listViewSupport;
    ArrayList<Support> lstSupport;
    SupportAdapter supportAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //khởi tạo một đối tượng View bằng cách sử dụng phương thức inflate() của LayoutInflater
        //Phương thức này được sử dụng để chuyển đổi bố cục từ tệp XML thành một đối tượng View.
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        //tìm kiếm thành phần ListView trong bố cục Fragment bằng cách sử dụng ID "lsv_Sp".
        //Chúng ta khởi tạo một ArrayList lstSupport với một danh sách các đối tượng Support.
        // Mỗi đối tượng Support đại diện cho một thành viên hỗ trợ của ứng dụng, và chứa tên, số điện thoại, email và hình ảnh của thành viên.
        listViewSupport = view.findViewById(R.id.lsv_Sp);
        lstSupport = new ArrayList<>();
        lstSupport.add(new Support(R.drawable.avt_dung,"Nguyễn Quốc Dũng","0923767834","QDung@gmail.com"));
        lstSupport.add(new Support(R.drawable.avt_qa,"Nguyễn Quỳnh Anh","09123445667","QuynhAnh@gmail.com"));
        lstSupport.add(new Support(R.drawable.avt_oanh,"Hà Hoàng Oanh","0934789221","Oanh@gmail.com"));
        lstSupport.add(new Support(R.drawable.avt_tuan,"Nguyễn Bá Tuấn","051234567","Tuan@gmail.com"));
        lstSupport.add(new Support(R.drawable.avt_kien,"Ngô Trung Kiên","0223876541","NgoKien@gmail.com"));
        //tạo một phiên bản của lớp SupportAdapter, truyền vào danh sách các đối tượng Support và ID tài nguyên bố cục cho mỗi hàng trong ListView.
        //Lớp SupportAdapter là một bộ chuyển đổi tùy chỉnh, mở rộng lớp ArrayAdapter, và được sử dụng để gắn dữ liệu Support vào ListView.
        //Cuối cùng, chúng ta gán supportAdapter cho ListView bằng cách sử dụng phương thức setAdapter().
        supportAdapter = new SupportAdapter(getActivity(),R.layout.member_support, lstSupport);
        listViewSupport.setAdapter(supportAdapter);

        return view;
    }
}