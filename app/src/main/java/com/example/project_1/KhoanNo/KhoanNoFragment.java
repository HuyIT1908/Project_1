package com.example.project_1.KhoanNo;

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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_1.Adapter.KhCHiAdapter;
import com.example.project_1.Adapter.KhoanNoAdapter;
import com.example.project_1.DAO.KHchiDAO;
import com.example.project_1.DAO.KhoanNoDAO;
import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.Models.KHchi;
import com.example.project_1.Models.KhoanNo;
import com.example.project_1.Models.NguoiDung;
import com.example.project_1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class KhoanNoFragment extends Fragment {

    private KhoanNoDAO khoanNoDAO;
    private NguoiDungDAO nguoiDungDAO;
    private ListView lv_ds_Khoan_No;
    private List<KhoanNo> list_Khoan_No = new ArrayList<>();
    private FloatingActionButton fbtn_add_Khoan_No;
    private List<NguoiDung> list_ND = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    final Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khoan_no, container, false);

        khoanNoDAO = new KhoanNoDAO( getActivity() );
        nguoiDungDAO = new NguoiDungDAO( getActivity() );
        lv_ds_Khoan_No = view.findViewById(R.id.lv_ds_Khoan_No);
        fbtn_add_Khoan_No = view.findViewById(R.id.fbtn_add_Khoan_No);

        fbtn_add_Khoan_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_Khoan_No();
            }
        });

        list_Khoan_No.clear();
        list_Khoan_No = khoanNoDAO.getAll_Khoan_No();
        KhoanNoAdapter adapter = new KhoanNoAdapter( getActivity() , list_Khoan_No);
        lv_ds_Khoan_No.setAdapter(adapter);

        try {
            lv_ds_Khoan_No.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    update_Khoan_No(position);
                }
            });
        } catch (Exception ex){
            Log.e("\t\tKhoanNoFrag : Error\t" , ex.toString());
        }

        return view;
    }

    private void add_Khoan_No() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_khoan_thu , null);

        TextInputLayout edt_ma_Khoan_No, edt_so_Tien_No , edt_ngay_No, edt_Chu_Thich;

        Button btn_huy = view.findViewById(R.id.btn_huy);
        Button btn_add_ke_hoach_chi = view.findViewById(R.id.btn_add_Khoan_Thu);
        Button btn_ngay_nhan_tien = view.findViewById(R.id.btn_ngay_nhan_tien);
        edt_ma_Khoan_No  = view.findViewById(R.id.edt_ma_thu_nhap);
        Spinner spinner_userName = view.findViewById(R.id.spinner_userName);
        edt_so_Tien_No  = view.findViewById(R.id.edt_so_Tien_THu);
        edt_ngay_No  = view.findViewById(R.id.edt_ngay_nhan_tien);
        edt_Chu_Thich  = view.findViewById(R.id.edt_Chu_Thich_khoan_thu);
        spinner_userName.setSelection(0);
        get_nguoi_Dung(spinner_userName);
        edt_ma_Khoan_No.setHint("Mã Khoản Nợ");
        edt_so_Tien_No.setHint("Số Tiền Nợ");
        edt_ngay_No.setHint("Ngày Nợ");


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder( getActivity() );
        builder.setView(view).setTitle("Thêm Khoản Nợ");
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        btn_ngay_nhan_tien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_time(edt_ngay_No);
            }
        });

        btn_add_ke_hoach_chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma_Khoan_No , userName , so_Tien_No , ngay_No , chu_Thich;
                ma_Khoan_No = edt_ma_Khoan_No.getEditText().getText().toString();
                userName = spinner_userName.getSelectedItem().toString();
                so_Tien_No = edt_so_Tien_No.getEditText().getText().toString();
                ngay_No = edt_ngay_No.getEditText().getText().toString();
                chu_Thich = edt_Chu_Thich.getEditText().getText().toString();
                String regex_so = "[0-9]+";

                if (  ma_Khoan_No.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Mã Khoản Nợ");

                } else if (  so_Tien_No.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Số Tiền Nợ");

                } else if ( ! so_Tien_No.matches(regex_so) ){

                    dialog_chung(0, getActivity(), "Số tiền phải nhập dạng Số");

                }
                else if (  ngay_No.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải chọn Ngày Nợ");

                } else {
                    try {
                        KhoanNo khoanNo = new KhoanNo(
                                "KN_" + ma_Khoan_No ,
                                userName ,
                                so_Tien_No ,
                                ngay_No ,
                                chu_Thich
                        );

                        if ( khoanNoDAO.insert_Khoan_no( khoanNo ) > 0) {

                            dialog_chung(1, getActivity(), "Thêm khoản Chi Thành Công");
                            dialog.dismiss();

                            list_Khoan_No.clear();
                            list_Khoan_No = khoanNoDAO.getAll_Khoan_No();
                            KhoanNoAdapter adapter = new KhoanNoAdapter( getActivity() , list_Khoan_No);
                            lv_ds_Khoan_No.setAdapter(adapter);

                        } else if ( khoanNoDAO.check_Khoan_No( khoanNo ) ) {

                            dialog_chung(0, getActivity(), "Khoản Nợ đã tồn tại !\n\nVui lòng nhập mã khác.");

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

                list_Khoan_No.clear();
                list_Khoan_No = khoanNoDAO.getAll_Khoan_No();
                KhoanNoAdapter adapter = new KhoanNoAdapter( getActivity() , list_Khoan_No);
                lv_ds_Khoan_No.setAdapter(adapter);
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

    private void update_Khoan_No(Integer position){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_khoan_thu , null);

        TextInputLayout edt_ma_Khoan_No, edt_so_Tien_No, edt_ngay_No, edt_Chu_Thich;

        Button btn_huy = view.findViewById(R.id.btn_huy);
        Button btn_add_ke_hoach_chi = view.findViewById(R.id.btn_add_Khoan_Thu);
        Button btn_ngay_nhan_tien = view.findViewById(R.id.btn_ngay_nhan_tien);
        edt_ma_Khoan_No  = view.findViewById(R.id.edt_ma_thu_nhap);
        Spinner spinner_userName = view.findViewById(R.id.spinner_userName);
        edt_so_Tien_No  = view.findViewById(R.id.edt_so_Tien_THu);
        edt_ngay_No  = view.findViewById(R.id.edt_ngay_nhan_tien);
        edt_Chu_Thich  = view.findViewById(R.id.edt_Chu_Thich_khoan_thu);
        spinner_userName.setSelection(0);
        get_nguoi_Dung(spinner_userName);
        edt_ma_Khoan_No.setHint("Mã Khoản Nợ");
        edt_so_Tien_No.setHint("Số Tiền Nợ");
        edt_ngay_No.setHint("Ngày Nợ");

        list_Khoan_No.clear();
        list_Khoan_No = khoanNoDAO.getAll_Khoan_No();
        edt_ma_Khoan_No.getEditText().setText( list_Khoan_No.get(position).getMaKhoanNo() );
        edt_ma_Khoan_No.getEditText().setEnabled(false);
        spinner_userName.setSelection( getIndex( spinner_userName , list_Khoan_No.get(position).getUserName() ) );
        edt_so_Tien_No.getEditText().setText( list_Khoan_No.get(position).getSoTienNo() );
        edt_ngay_No.getEditText().setText( list_Khoan_No.get(position).getNgayNo() );
        edt_Chu_Thich.getEditText().setText( list_Khoan_No.get(position).getChuThich() );
        btn_add_ke_hoach_chi.setText("Cập Nhật");

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setView(view).setTitle("Cập Nhật Khoản Nợ");
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        btn_ngay_nhan_tien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_time(edt_ngay_No);
            }
        });

        btn_add_ke_hoach_chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma_Khoan_No , userName , so_Tien_No , ngay_No , chu_Thich;
                ma_Khoan_No = edt_ma_Khoan_No.getEditText().getText().toString();
                userName = spinner_userName.getSelectedItem().toString();
                so_Tien_No = edt_so_Tien_No.getEditText().getText().toString();
                ngay_No = edt_ngay_No.getEditText().getText().toString();
                chu_Thich = edt_Chu_Thich.getEditText().getText().toString();
                String regex_so = "[0-9]+";

                if (  ma_Khoan_No.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Mã Khoản Nợ");

                } else if (  so_Tien_No.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Số Nợ");

                } else if ( ! so_Tien_No.matches(regex_so) ){

                    dialog_chung(0, getActivity(), "Số tiền phải dạng nhập Số");

                }
                else if (  ngay_No.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải chọn Ngày Nợ");

                } else {
                    try {
                        KhoanNo khoanNo = new KhoanNo(
                                ma_Khoan_No ,
                                userName ,
                                so_Tien_No ,
                                ngay_No ,
                                chu_Thich
                        );

                        if ( khoanNoDAO.update_Khoan_NO( khoanNo ) > 0) {

                            dialog_chung(1, getActivity(), "Cập Nhật Khoản Nợ Thành Công");
                            dialog.dismiss();

                            list_Khoan_No.clear();
                            list_Khoan_No = khoanNoDAO.getAll_Khoan_No();
                            KhoanNoAdapter adapter = new KhoanNoAdapter( getActivity() , list_Khoan_No);
                            lv_ds_Khoan_No.setAdapter(adapter);

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

                list_Khoan_No.clear();
                list_Khoan_No = khoanNoDAO.getAll_Khoan_No();
                KhoanNoAdapter adapter = new KhoanNoAdapter( getActivity() , list_Khoan_No);
                lv_ds_Khoan_No.setAdapter(adapter);
            }
        });

        dialog.show();
    }
}