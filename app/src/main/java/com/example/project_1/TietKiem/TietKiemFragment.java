package com.example.project_1.TietKiem;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_1.Adapter.KhoanNoAdapter;
import com.example.project_1.Adapter.TietKiemAdapter;
import com.example.project_1.DAO.KhoanNoDAO;
import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.DAO.TietKiemDAO;
import com.example.project_1.Models.KhoanNo;
import com.example.project_1.Models.NguoiDung;
import com.example.project_1.Models.TietKiem;
import com.example.project_1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class TietKiemFragment extends Fragment {

    private TietKiemDAO tietKiemDAO;
    private NguoiDungDAO nguoiDungDAO;
    private ListView lv_ds_Tiet_Kiem;
    private List<TietKiem> list_Tiet_Kiem = new ArrayList<>();
    private FloatingActionButton fbtn_add_Tiet_Kiem;
    private List<NguoiDung> list_ND = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    final Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tiet_kiem, container, false);

        tietKiemDAO = new TietKiemDAO( getActivity() );
        nguoiDungDAO = new NguoiDungDAO( getActivity() );
        lv_ds_Tiet_Kiem = view.findViewById(R.id.lv_ds_Tiet_Kiem);
        fbtn_add_Tiet_Kiem = view.findViewById(R.id.fbtn_add_Tiet_Kiem);

        fbtn_add_Tiet_Kiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_Tiet_Kiem();
            }
        });

        list_Tiet_Kiem.clear();
        list_Tiet_Kiem = tietKiemDAO.getAll_Tiet_Kiem();
        TietKiemAdapter adapter = new TietKiemAdapter( getActivity() , list_Tiet_Kiem);
        lv_ds_Tiet_Kiem.setAdapter(adapter);

        try {
            lv_ds_Tiet_Kiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    update_Tiet_Kiem(position);
                }
            });
        } catch (Exception ex){
            Log.e("\t\tTietKiemFrag : Error\t" , ex.toString());
        }

        return view;
    }

    private void add_Tiet_Kiem() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_tiet_kiem , null);

        TextInputLayout edt_ma_Tiet_Kiem, edt_so_Tien_Tiet_Kiem , edt_ngay_Tiet_Kiem, edt_Chu_Thich;

        Button btn_huy = view.findViewById(R.id.btn_huy_tiet_Kiem);
        Button btn_add_Tiet_kiem = view.findViewById(R.id.btn_add_Tiet_Kiem);
        Button btn_ngay_nhan_tien = view.findViewById(R.id.btn_ngay_Tiet_Kiem);
        CheckBox cbk_Status = view.findViewById(R.id.ckb_tiet_Kiem);

        edt_ma_Tiet_Kiem  = view.findViewById(R.id.edt_ma_Tiet_Kiem);
        Spinner spinner_userName = view.findViewById(R.id.spinner_userName_Tiet_kiem);
        edt_so_Tien_Tiet_Kiem  = view.findViewById(R.id.edt_so_Tien_Tiet_KIem);
        edt_ngay_Tiet_Kiem  = view.findViewById(R.id.edt_ngay_nhan_tien_Tiet_Kiem);
        edt_Chu_Thich  = view.findViewById(R.id.edt_Chu_Thich_Tiet_Kiem);
        spinner_userName.setSelection(0);
        get_nguoi_Dung(spinner_userName);
        edt_ma_Tiet_Kiem.setHint("Mã Tiết Kiệm");
        edt_so_Tien_Tiet_Kiem.setHint("Số Tiền Tiết Kiệm");
        edt_ngay_Tiet_Kiem.setHint("Ngày Tiết Kiệm");
        cbk_Status.setText("Chưa Tiết Kiệm");
        edt_ma_Tiet_Kiem.setEnabled(false);
        set_Status(cbk_Status);


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder( getActivity() );
        builder.setView(view).setTitle("Thêm Tiết Kiệm");
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        btn_ngay_nhan_tien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_time(edt_ngay_Tiet_Kiem);
            }
        });

        btn_add_Tiet_kiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma_Tiet_Kiem , userName , so_Tien_Tiet_Kiem , ngay_Tiet_Kiem , chu_Thich;
                ma_Tiet_Kiem = edt_ma_Tiet_Kiem.getEditText().getText().toString();
                userName = spinner_userName.getSelectedItem().toString();
                so_Tien_Tiet_Kiem = edt_so_Tien_Tiet_Kiem.getEditText().getText().toString();
                ngay_Tiet_Kiem = edt_ngay_Tiet_Kiem.getEditText().getText().toString();
                chu_Thich = edt_Chu_Thich.getEditText().getText().toString();
                String status = String.valueOf( cbk_Status.isChecked() );
                String regex_so = "[0-9]+";

                if (  so_Tien_Tiet_Kiem.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Số Tiền Tiết Kiệm");

                } else if ( ! so_Tien_Tiet_Kiem.matches(regex_so) ){

                    dialog_chung(0, getActivity(), "Số tiền phải nhập dạng Số");

                }
                else if (  ngay_Tiet_Kiem.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải chọn Ngày Tiết Kiệm");

                } else if ( (Integer.parseInt(so_Tien_Tiet_Kiem)) == 0
                            || (Integer.parseInt(so_Tien_Tiet_Kiem)) < 0){

                    dialog_chung(0, getActivity(), "Số tiền Phải > 0");

                } else {
                    try {
                        TietKiem tietKiem = new TietKiem(
                                "TK_" + System.currentTimeMillis() ,
                                userName ,
                                so_Tien_Tiet_Kiem ,
                                ngay_Tiet_Kiem ,
                                chu_Thich ,
                                status
                        );

                        String[] get_tk = tietKiem.getUserName().split(" | ");
                        String get_user = get_tk[0];

                        NguoiDung nd = list_ND.get( get_vi_tri(list_ND , get_user) );

                        Integer so_tien_CHi = Integer.parseInt( so_Tien_Tiet_Kiem );
                        Integer tong_tien_TK = Integer.parseInt( nd.getTongSoTien() );

                        int so_tien =  tong_tien_TK - so_tien_CHi;

//                        boolean kq = so_tien_CHi > tong_tien_TK;
//                        Log.e("----------------------\t" , "\t\t\t" + so_tien +
//                                " " + tong_tien_TK + " " + so_tien_CHi + " |" + kq);
                        if ( so_tien_CHi > tong_tien_TK ){

                            dialog_chung(0, getActivity(), "Bạn Không đủ tiền chi trả");

                        } else if ( so_tien_CHi == 0 ||
                                    so_tien < 0){

                            dialog_chung(0, getActivity(), "Số tiền chi phải > 0");

                        } else if ( tietKiemDAO.inser_Tiet_Kiem( tietKiem ) > 0) {

                            nd.setTongSoTien(String.valueOf(so_tien));
                            nguoiDungDAO.updateNguoiDung(nd);
                            tietKiem.setUserName(nd.toString());
                            //                        boolean kq = userName.equals( list_ND.get( get_vi_tri(list_ND , get_user) ).toString() );
//                        Log.e("\t\t" + userName , nd.toString()
//                                + " | " + String.valueOf( " " + kq +" -- ") + get_user + "\t");

                            if ( tietKiemDAO.update_Tiet_Kiem( tietKiem ) > 0 ){

                            }

                            dialog_chung(1, getActivity(), "Thêm Tiết Kiệm Thành Công");
                            dialog.dismiss();

                            list_Tiet_Kiem.clear();
                            list_Tiet_Kiem = tietKiemDAO.getAll_Tiet_Kiem();
                            TietKiemAdapter adapter = new TietKiemAdapter( getActivity() , list_Tiet_Kiem);
                            lv_ds_Tiet_Kiem.setAdapter(adapter);

                        } else if ( tietKiemDAO.chech_Tiet_kiem( tietKiem ) ) {

                            dialog_chung(0, getActivity(), "Mã Tiết Kiệm đã tồn tại !\n\nVui lòng nhập mã khác.");

                        } else {

                            dialog_chung(1, getActivity(), "Thêm Thất Bại");
                        }

                    } catch (Exception ex) {
                        Log.e("Error Thêm  : \t\t", ex.toString());
                    }
                }

            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                list_Tiet_Kiem.clear();
                list_Tiet_Kiem = tietKiemDAO.getAll_Tiet_Kiem();
                TietKiemAdapter adapter = new TietKiemAdapter( getActivity() , list_Tiet_Kiem);
                lv_ds_Tiet_Kiem.setAdapter(adapter);
            }
        });

        dialog.show();
    }

    private void dialog_chung(Integer so, Context context, String tb) {
        switch (so) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông Báo").setMessage(tb);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case 1:
                Toast.makeText(context, tb, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void get_nguoi_Dung(Spinner spinner) {
        list_ND = nguoiDungDAO.getAllNguoiDung();

        ArrayAdapter<NguoiDung> dataAdapter = new ArrayAdapter<NguoiDung>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                list_ND
        );

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void get_time(TextInputLayout edt){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity() ,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
                        edt.getEditText().setText(sdf.format(cal.getTime()));
                    }
                } , calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }

    private void update_Tiet_Kiem(Integer position){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_tiet_kiem , null);

        TextInputLayout edt_ma_Tiet_Kiem, edt_so_Tien_Tiet_Kiem, edt_ngay_Tiet_Kiem, edt_Chu_Thich;

        Button btn_huy = view.findViewById(R.id.btn_huy_tiet_Kiem);
        Button btn_update_Tiet_Kiem = view.findViewById(R.id.btn_add_Tiet_Kiem);
        Button btn_ngay_nhan_tien = view.findViewById(R.id.btn_ngay_Tiet_Kiem);
        CheckBox cbk_Status = view.findViewById(R.id.ckb_tiet_Kiem);

        edt_ma_Tiet_Kiem  = view.findViewById(R.id.edt_ma_Tiet_Kiem);
        Spinner spinner_userName = view.findViewById(R.id.spinner_userName_Tiet_kiem);
        edt_so_Tien_Tiet_Kiem  = view.findViewById(R.id.edt_so_Tien_Tiet_KIem);
        edt_ngay_Tiet_Kiem  = view.findViewById(R.id.edt_ngay_nhan_tien_Tiet_Kiem);
        edt_Chu_Thich  = view.findViewById(R.id.edt_Chu_Thich_Tiet_Kiem);
        spinner_userName.setSelection(0);
        get_nguoi_Dung(spinner_userName);
        edt_ma_Tiet_Kiem.setHint("Mã Tiết Kiệm");
        edt_so_Tien_Tiet_Kiem.setHint("Số Tiền Tiết Kiệm");
        edt_ngay_Tiet_Kiem.setHint("Ngày Tiết Kiệm");
        cbk_Status.setText("Chưa Tiết Kiệm");
        edt_ma_Tiet_Kiem.setEnabled(false);
        set_Status(cbk_Status);

        list_Tiet_Kiem.clear();
        list_Tiet_Kiem = tietKiemDAO.getAll_Tiet_Kiem();
        TietKiemAdapter adapter = new TietKiemAdapter( getActivity() , list_Tiet_Kiem);
        lv_ds_Tiet_Kiem.setAdapter(adapter);
        edt_ma_Tiet_Kiem.getEditText().setText( list_Tiet_Kiem.get(position).getMaTietKiem() );
        edt_ma_Tiet_Kiem.getEditText().setEnabled(false);
        spinner_userName.setSelection( getIndex( spinner_userName , list_Tiet_Kiem.get(position).getUserName() ) );
        edt_so_Tien_Tiet_Kiem.getEditText().setText( list_Tiet_Kiem.get(position).getSoTienTietKiem() );
        edt_ngay_Tiet_Kiem.getEditText().setText( list_Tiet_Kiem.get(position).getNgayTietKiem() );
        edt_Chu_Thich.getEditText().setText( list_Tiet_Kiem.get(position).getChuThich() );

        if ( Boolean.parseBoolean( list_Tiet_Kiem.get(position).getStatus() ) ){
            cbk_Status.setChecked( Boolean.parseBoolean( list_Tiet_Kiem.get(position).getStatus() ) );
            cbk_Status.setText("Đã Tiết Kiệm");
        } else {
            cbk_Status.setChecked( Boolean.parseBoolean( list_Tiet_Kiem.get(position).getStatus() ) );
            cbk_Status.setText("Chưa Tiết Kiệm");
        }
        btn_update_Tiet_Kiem.setText("Cập Nhật");

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setView(view).setTitle("Cập Nhật Tiết Kiệm");
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        btn_ngay_nhan_tien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_time(edt_ngay_Tiet_Kiem);
            }
        });

        btn_update_Tiet_Kiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma_Tiet_Kiem , userName , so_Tien_Tiet_Kiem , ngay_Tiet_Kiem , chu_Thich;
                ma_Tiet_Kiem = edt_ma_Tiet_Kiem.getEditText().getText().toString();
                userName = spinner_userName.getSelectedItem().toString();
                so_Tien_Tiet_Kiem = edt_so_Tien_Tiet_Kiem.getEditText().getText().toString();
                ngay_Tiet_Kiem = edt_ngay_Tiet_Kiem.getEditText().getText().toString();
                chu_Thich = edt_Chu_Thich.getEditText().getText().toString();
                String regex_so = "[0-9]+";
                String status = String.valueOf( cbk_Status.isChecked() );

                if (  so_Tien_Tiet_Kiem.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Số Tiền Tiết Kiệm");

                } else if ( ! so_Tien_Tiet_Kiem.matches(regex_so) ){

                    dialog_chung(0, getActivity(), "Số tiền phải dạng nhập Số");

                }
                else if (  ngay_Tiet_Kiem.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải chọn Ngày Tiết Kiệm");

                } else {
                    try {
                        TietKiem tietKiem = new TietKiem(
                                ma_Tiet_Kiem ,
                                userName ,
                                so_Tien_Tiet_Kiem ,
                                ngay_Tiet_Kiem ,
                                chu_Thich ,
                                status
                        );

                        if ( tietKiemDAO.update_Tiet_Kiem( tietKiem ) > 0) {

                            dialog_chung(1, getActivity(), "Cập Nhật Tiết Kiệm Thành Công");
                            dialog.dismiss();

                            list_Tiet_Kiem.clear();
                            list_Tiet_Kiem = tietKiemDAO.getAll_Tiet_Kiem();
                            TietKiemAdapter adapter = new TietKiemAdapter( getActivity() , list_Tiet_Kiem);
                            lv_ds_Tiet_Kiem.setAdapter(adapter);

                        } else {

                            dialog_chung(1, getActivity(), "Cập Nhật Thất Bại");
                        }

                    } catch (Exception ex) {
                        Log.e("Error Cập Nhật  : \t\t", ex.toString());
                    }
                }

            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                list_Tiet_Kiem.clear();
                list_Tiet_Kiem = tietKiemDAO.getAll_Tiet_Kiem();
                TietKiemAdapter adapter = new TietKiemAdapter( getActivity() , list_Tiet_Kiem);
                lv_ds_Tiet_Kiem.setAdapter(adapter);
            }
        });

        dialog.show();
    }

    private Integer get_vi_tri(List<NguoiDung> nd , String user){
        for (int i = 0; i < nd.size() ; i++) {
            if ( nd.get(i).getUserName().equals( user ) ){
                return i;
            }
        }
        return 0;
    }

    private void set_Status(CheckBox cbk){
        cbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( cbk.isChecked() ){
                    cbk.setText("Đã Tiết Kiệm");
                } else {
                    cbk.setText("Chưa Tiết Kiệm");
                }
            }
        });
    }
}