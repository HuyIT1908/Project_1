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

    public KhCHiAdapter(Context context, List<KHchi> list) {
        super();
        this.context = context;
        this.list = list;
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
            convertView = inflater.inflate(R.layout.show_chung , null);

            viewHoler.maDuChi = convertView.findViewById(R.id.tv_chung_ma);
            viewHoler.userName = convertView.findViewById(R.id.tv_chung_tk);
            viewHoler.soTienDuChi = convertView.findViewById(R.id.tv_chung_so_tien);
            viewHoler.ngayDuChi = convertView.findViewById(R.id.tv_chung_ngay);
            viewHoler.chu_THich = convertView.findViewById(R.id.tv_chung_chu_thich);
            viewHoler.edit = convertView.findViewById(R.id.img_chung_edit);
            viewHoler.delete = convertView.findViewById(R.id.img_chung_delete);

            ((TextView) convertView.findViewById(R.id.tv_chung_so_1)).setText("Tài Khoản :");
            ((TextView) convertView.findViewById(R.id.tv_chung_so_2)).setText("Số Tiền Dự chi :");
            ((TextView) convertView.findViewById(R.id.tv_chung_so_3)).setText("Ngày Dự Chi :");
            ((TextView) convertView.findViewById(R.id.tv_chung_so_4)).setText("Ghi chú :");

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

        return convertView;
    }

    private static class ViewHoler{
        TextView maDuChi;
        TextView userName;
        TextView soTienDuChi;
        TextView ngayDuChi;
        TextView chu_THich;
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
