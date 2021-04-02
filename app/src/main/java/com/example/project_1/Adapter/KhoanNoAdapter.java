package com.example.project_1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1.DAO.KhoanNoDAO;
import com.example.project_1.Models.KhoanNo;
import com.example.project_1.R;

import java.util.ArrayList;
import java.util.List;

public class KhoanNoAdapter extends BaseAdapter {
    Context context;
    List<KhoanNo> list = new ArrayList<>();
    KhoanNoDAO khoanNoDAO;

    public KhoanNoAdapter(Context context, List<KhoanNo> list) {
        super();
        this.context = context;
        this.list = list;
        khoanNoDAO = new KhoanNoDAO(context);
    }

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
        ViewHoler viewHoler;
        if (convertView == null){
            viewHoler = new ViewHoler();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.show_tiet_kiem , null);

            viewHoler.maKhoanNo = convertView.findViewById(R.id.tv_chung_ma_Tiet_Kiem);
            viewHoler.userName = convertView.findViewById(R.id.tv_chung_tk_Tiet_Kiem);
            viewHoler.soTienNo = convertView.findViewById(R.id.tv_chung_so_tien_Tiet_Kiem);
            viewHoler.ngayNo = convertView.findViewById(R.id.tv_chung_ngay_Tiet_Kiem);
            viewHoler.chu_THich = convertView.findViewById(R.id.tv_chung_chu_thich_Tiet_Kiem);
            viewHoler.edit = convertView.findViewById(R.id.img_chung_edit_Tiet_kiem);
            viewHoler.delete = convertView.findViewById(R.id.img_chung_delete_Tiet_Kiem);
            viewHoler.status = convertView.findViewById(R.id.tv_chung_Status_Tiet_Kiem);

            ((TextView) convertView.findViewById(R.id.tv_chung_so_1_TIet_Kiem)).setText("Tài Khoản :");
            ((TextView) convertView.findViewById(R.id.tv_chung_so_2_Tiet_Kiem)).setText("Số Tiền Vay :");
            ((TextView) convertView.findViewById(R.id.tv_chung_so_3_Tiet_Kiem)).setText("Ngày Vay :");
            ((TextView) convertView.findViewById(R.id.tv_chung_so_4_Tiet_Kiem)).setText("Ghi chú :");
            ((TextView) convertView.findViewById(R.id.tv_chung_so_5_Tiet_Kiem)).setText("Trạng Thái :");

            viewHoler.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Thông báo").setMessage("Bạn có chắc chắn muốn xóa Khoản Vay này không ?");
                    builder.setNegativeButton("Xóa",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    khoanNoDAO.delete_Khoan_No_By_ID( list.get(position).getMaKhoanNo() );
                                    list.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Đã xóa Khoản Nợ thành công",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                    builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }

        viewHoler.maKhoanNo.setText( list.get(position).getMaKhoanNo() );
        viewHoler.userName.setText( list.get(position).getUserName() );
        viewHoler.soTienNo.setText( list.get(position).getSoTienNo() );
        viewHoler.ngayNo.setText( list.get(position).getNgayNo() );
        viewHoler.chu_THich.setText( list.get(position).getChuThich() );

        if ( list.get(position).getStatus().equalsIgnoreCase( "true" ) ){
            viewHoler.status.setText("Đã Trả");
        } else {
            viewHoler.status.setText("Chưa Trả");
        }

        return convertView;
    }

    private static class ViewHoler{
        TextView maKhoanNo;
        TextView userName;
        TextView soTienNo;
        TextView ngayNo;
        TextView chu_THich;
        TextView status;
        ImageView delete;
        ImageView edit;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
    }
}
