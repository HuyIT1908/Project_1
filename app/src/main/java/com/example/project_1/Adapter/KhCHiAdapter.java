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

import com.example.project_1.DAO.KHchiDAO;
import com.example.project_1.Models.KHchi;
import com.example.project_1.R;

import java.util.ArrayList;
import java.util.List;

public class KhCHiAdapter extends BaseAdapter {
    Context context;
    List<KHchi> list = new ArrayList<>();
    KHchiDAO kHchiDAO;
    TextView tv_so_tien;

    public KhCHiAdapter(Context context, List<KHchi> list, TextView tv_so_tien) {
        super();
        this.context = context;
        this.list = list;
        this.tv_so_tien = tv_so_tien;
        kHchiDAO = new KHchiDAO(context);
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

            viewHoler.maDuChi = convertView.findViewById(R.id.tv_chung_ma_Tiet_Kiem);
            viewHoler.userName = convertView.findViewById(R.id.tv_chung_tk_Tiet_Kiem);
            viewHoler.soTienDuChi = convertView.findViewById(R.id.tv_chung_so_tien_Tiet_Kiem);
            viewHoler.ngayDuChi = convertView.findViewById(R.id.tv_chung_ngay_Tiet_Kiem);
            viewHoler.chu_THich = convertView.findViewById(R.id.tv_chung_chu_thich_Tiet_Kiem);
            viewHoler.edit = convertView.findViewById(R.id.img_chung_edit_Tiet_kiem);
            viewHoler.delete = convertView.findViewById(R.id.img_chung_delete_Tiet_Kiem);
            viewHoler.status = convertView.findViewById(R.id.tv_chung_Status_Tiet_Kiem);

            ((TextView) convertView.findViewById(R.id.tv_chung_so_1_TIet_Kiem)).setText("Tài Khoản :");
            ((TextView) convertView.findViewById(R.id.tv_chung_so_2_Tiet_Kiem)).setText("Số Tiền DỰ Chi :");
            ((TextView) convertView.findViewById(R.id.tv_chung_so_3_Tiet_Kiem)).setText("Ngày Dự Chi :");
            ((TextView) convertView.findViewById(R.id.tv_chung_so_4_Tiet_Kiem)).setText("Ghi chú :");
            ((TextView) convertView.findViewById(R.id.tv_chung_so_5_Tiet_Kiem)).setText("Trạng Thái :");

            viewHoler.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Thông báo").setMessage("Bạn có chắc chắn muốn xóa Kế Hoạch Dự Chi này không ?");
                    builder.setNegativeButton("Xóa",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    kHchiDAO.delete_Ke_hoach_Chi_By_ID( list.get(position).getMaDuChi() );
                                    list.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Đã xóa Kế Hoạch CHi thành công",
                                            Toast.LENGTH_SHORT).show();

                                    tv_so_tien.setText("Số tiền dự chi : " + kHchiDAO.get_GT("SELECT sum(soTienDuChi) FROM KeHoachChi;") );
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

        viewHoler.maDuChi.setText( list.get(position).getMaDuChi() );
        viewHoler.userName.setText( list.get(position).getUserName() );
        viewHoler.soTienDuChi.setText( list.get(position).getSoTienDuChi() );
        viewHoler.ngayDuChi.setText( list.get(position).getNgayDuChi() );
        viewHoler.chu_THich.setText( list.get(position).getChuThich() );

        if ( list.get(position).getStatus().equalsIgnoreCase( "true" ) ){
            viewHoler.status.setText("Đã Chi");
        } else {
            viewHoler.status.setText("Chưa Chi");
        }
        return convertView;
    }

    private static class ViewHoler{
        TextView maDuChi;
        TextView userName;
        TextView soTienDuChi;
        TextView ngayDuChi;
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
