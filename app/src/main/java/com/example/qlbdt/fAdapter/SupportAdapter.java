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

public class SupportAdapter extends BaseAdapter {
    Activity context;
    int Resource;
    ArrayList<Support> objects;

    public SupportAdapter(Activity context, int resource, ArrayList<Support> objects) {
        this.context = context;
        Resource = resource;
        this.objects = objects;
    }
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
