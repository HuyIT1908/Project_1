package com.example.project_1.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project_1.Models.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class DSnguoiDungAdapter extends BaseAdapter {
    Context context;
    List<NguoiDung> list = new ArrayList<>();

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private static class ViewHoler{
        TextView userName;
        TextView passWord;
        TextView hoTen;
        TextView sdt;
    }
}
