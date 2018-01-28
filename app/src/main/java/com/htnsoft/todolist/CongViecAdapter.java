package com.htnsoft.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by TRUNG VAN on 1/27/2018.
 */

public class CongViecAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private ArrayList<CongViec> congvieclist;

    public CongViecAdapter(MainActivity context, int layout, ArrayList<CongViec> congvieclist) {
        this.context = context;
        this.layout = layout;
        this.congvieclist = congvieclist;
    }

    @Override
    public int getCount() {
        return congvieclist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private  class ViewHolder{
        TextView txtTenCV;
        ImageView imgDelete, imgEdit;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTenCV = (TextView) convertView.findViewById(R.id.txtCongViec);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.imgEdit);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();

        }

        final CongViec congViec = congvieclist.get(position);

        holder.txtTenCV.setText(congViec.getTenCV());

        //Bat su kien xoa va sua

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaCV(congViec.getTenCV(), congViec.getIdCV());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaCV(congViec.getTenCV(), congViec.getIdCV());
            }
        });


        return convertView;
    }
}
