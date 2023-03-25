package com.example.qlbdt.fAdapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qlbdt.R;
import com.example.qlbdt.fObject.Support;

import java.util.ArrayList;
//được sử dụng để hiển thị danh sách các đối tượng Support trong một ListView.
public class SupportAdapter extends BaseAdapter {
    //Các trường Activity context, int Resource và ArrayList<Support> objects được khai báo để lưu trữ thông tin về hoạt động hiện tại,
    // tài nguyên và đối tượng Support được hiển thị trong danh sách.
    Activity context;
    int Resource;
    ArrayList<Support> objects;

    //Phương thức khởi tạo SupportAdapter chấp nhận ba tham số, đó là activity hiện tại,
    // tài nguyên để hiển thị cho từng mục và danh sách các đối tượng Support.
    public SupportAdapter(Activity context, int resource, ArrayList<Support> objects) {
        this.context = context;
        Resource = resource;
        this.objects = objects;
    }
    //Phương thức getCount được ghi đè để trả về số lượng phần tử trong danh sách objects.
    //Phương thức getItem và getItemId được ghi đè và không được sử dụng trong trường hợp này.
    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //Phương thức quan trọng nhất là getView, nó được ghi đè để tạo và trả về một View để hiển thị cho mỗi phần tử trong danh sách.
    // getView sử dụng LayoutInflater để tạo một View từ tài nguyên Resource cho mỗi phần tử trong danh sách objects.
    // Nó lấy các thuộc tính nameSpOanh, imageSpOanh, phoneSpOanh và emailSpOanh từ View và thiết lập giá trị của chúng tương ứng với các trường
    // trong đối tượng Support.
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        view = layoutInflater.inflate(Resource, null);
        ImageView imageSpOanh = view.findViewById(R.id.imageSpOanh);
        TextView nameSpOanh = view.findViewById(R.id.nameSpOanh);
        ImageView phoneSpOanh = view.findViewById(R.id.phoneSpOanh);
        ImageView emailSpOanh = view.findViewById(R.id.mailSpOanh);
        Support support = this.objects.get(i);

        nameSpOanh.setText(support.getNameSp());
        imageSpOanh.setImageResource(support.getImageSp());

        Support s = objects.get(i);
        //phoneSpOanh và emailSpOanh được thiết lập để bắt sự kiện nhấn vào nút, khi nhấn vào,
        // chúng sẽ mở ứng dụng điện thoại và ứng dụng Email trên điện thoại để thực hiện cuộc gọi và gửi email tới người hỗ trợ.
        // Nếu không có ứng dụng email trên điện thoại của người dùng, một thông báo sẽ hiển thị.
        // Cuối cùng, phương thức trả về View để hiển thị cho mỗi phần tử trong danh sách objects.
        phoneSpOanh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent CallSp = new Intent();
                CallSp.setAction(Intent.ACTION_DIAL);
                CallSp.setData(Uri.parse("tel:" + s.getPhoneSp()));
                context.startActivity(CallSp);
            }
        });
        emailSpOanh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent MailSp = new Intent(Intent.ACTION_SENDTO);
                MailSp.setData(Uri.parse("mailto:" + s.getMailSp()));
                try {
                    context.startActivity(MailSp);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "Ứng dụng Email không tồn tại trên thiết bị của bạn", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
