package com.example.project_1.Chi;

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

import com.example.project_1.Adapter.ChiAdapter;
import com.example.project_1.Adapter.ThuAdapter;
import com.example.project_1.DAO.ChiDAO;
import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.Models.Chi;
import com.example.project_1.Models.NguoiDung;
import com.example.project_1.Models.Thu;
import com.example.project_1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ChiFragment extends Fragment {

    private ChiDAO chiDAO;
    private NguoiDungDAO nguoiDungDAO;
    private ListView lv_ds_Chi;
    private List<Chi> list_CHi = new ArrayList<>();
    private FloatingActionButton fbtn_add_khoan_Chi;
    private List<NguoiDung> list_ND = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    final Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi, container, false);

        chiDAO = new ChiDAO( getActivity() );
        nguoiDungDAO = new NguoiDungDAO( getActivity() );
        lv_ds_Chi = view.findViewById(R.id.lv_ds_Chi);
        fbtn_add_khoan_Chi = view.findViewById(R.id.fbtn_add_khoan_Chi);

        fbtn_add_khoan_Chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_Khoan_Chi();
            }
        });

        list_CHi.clear();
        list_CHi = chiDAO.getAll_Khoan_Chi() ;
        ChiAdapter adapter = new ChiAdapter(getActivity() , list_CHi);
        lv_ds_Chi.setAdapter(adapter);

        try {
            lv_ds_Chi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    update_Khoan_Chi(position);
                }
            });
        } catch (Exception ex){
            Log.e("\t\tChi Fragment : Error\t" , ex.toString());
        }

        return view;
    }

    private void add_Khoan_Chi() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_khoan_thu , null);

        TextInputLayout edt_ma_chi_tieu, edt_so_tien_chi , edt_ngay_Chi, edt_Chu_Thich;

        Button btn_add_Khoan_Thu = view.findViewById(R.id.btn_add_Khoan_Thu);
        Button btn_ngay_nhan_tien = view.findViewById(R.id.btn_ngay_nhan_tien);
        edt_ma_chi_tieu  = view.findViewById(R.id.edt_ma_thu_nhap);
        Spinner spinner_userName = view.findViewById(R.id.spinner_userName);
        edt_so_tien_chi  = view.findViewById(R.id.edt_so_Tien_THu);
        edt_ngay_Chi  = view.findViewById(R.id.edt_ngay_nhan_tien);
        edt_Chu_Thich  = view.findViewById(R.id.edt_Chu_Thich_khoan_thu);
        spinner_userName.setSelection(0);
        get_nguoi_Dung(spinner_userName);
        edt_ma_chi_tieu.getEditText().setHint("Mã Chi Tiêu");
        edt_so_tien_chi.getEditText().setHint("Số Tiền Chi");
        edt_ngay_Chi.getEditText().setHint("Ngày Chi");


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder( getActivity() );
        builder.setView(view).setTitle("Thêm Khoản Chi");
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        btn_ngay_nhan_tien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_time(edt_ngay_Chi);
            }
        });

        btn_add_Khoan_Thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma_chi_tieu , userName , so_tien_chi , ngay_Chi , chu_Thich;
                ma_chi_tieu = edt_ma_chi_tieu.getEditText().getText().toString();
                userName = spinner_userName.getSelectedItem().toString();
                so_tien_chi = edt_so_tien_chi.getEditText().getText().toString();
                ngay_Chi = edt_ngay_Chi.getEditText().getText().toString();
                chu_Thich = edt_Chu_Thich.getEditText().getText().toString();
                String regex_so = "[0-9]+";

                if (  ma_chi_tieu.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Mã Chi Tiêu");

                } else if (  so_tien_chi.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Số Tiền Chi");

                } else if ( ! so_tien_chi.matches(regex_so) ){

                    dialog_chung(0, getActivity(), "Số tiền phải nhập dạng Số");

                }
                else if (  ngay_Chi.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải chọn Ngày Chi");

                } else {
                    try {
                        Chi chi = new Chi(
                                ma_chi_tieu ,
                                userName ,
                                so_tien_chi ,
                                ngay_Chi ,
                                chu_Thich
                        );

                        if ( chiDAO.inser_Khoan_Chi( chi ) > 0) {

                            dialog_chung(1, getActivity(), "Thêm khoản Chi Thành Công");
                            dialog.dismiss();

                            list_CHi.clear();
                            list_CHi = chiDAO.getAll_Khoan_Chi() ;
                            ChiAdapter adapter = new ChiAdapter( getActivity() , list_CHi );
                            lv_ds_Chi.setAdapter(adapter);

                        } else if ( chiDAO.check(chi) ) {

                            dialog_chung(0, getActivity(), "Khoản Chi đã tồn tại !\n\nVui lòng nhập mã khác.");

                        } else {

                            dialog_chung(1, getActivity(), "Thêm Thất Bại");
                        }

                    } catch (Exception ex) {
                        Log.e("Error Đăng Kí  : \t\t", ex.toString());
                    }
                }

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
}