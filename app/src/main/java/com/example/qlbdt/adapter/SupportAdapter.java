package com.example.qlbdt.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.qlbdt.object.Support;

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

        Support support = objects.get(i);


        return view;
    }
}
