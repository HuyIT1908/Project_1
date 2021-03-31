package com.example.project_1.Ke_Hoach_chi;

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
import com.example.project_1.Adapter.KhCHiAdapter;
import com.example.project_1.DAO.ChiDAO;
import com.example.project_1.DAO.KHchiDAO;
import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.Models.Chi;
import com.example.project_1.Models.KHchi;
import com.example.project_1.Models.NguoiDung;
import com.example.project_1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class KhChiFragment extends Fragment {

    private KHchiDAO kHchiDAO;
    private NguoiDungDAO nguoiDungDAO;
    private ListView lv_ds_ke_hoach_Chi;
    private List<KHchi> list_KhChi = new ArrayList<>();
    private FloatingActionButton fbtn_add_ke_hoach_Chi;
    private List<NguoiDung> list_ND = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    final Calendar calendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kh_chi, container, false);

        kHchiDAO = new KHchiDAO( getActivity() );
        nguoiDungDAO = new NguoiDungDAO( getActivity() );
        lv_ds_ke_hoach_Chi = view.findViewById(R.id.lv_ds_ke_hoach_Chi);
        fbtn_add_ke_hoach_Chi = view.findViewById(R.id.fbtn_add_ke_hoach_Chi);

        fbtn_add_ke_hoach_Chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_Ke_hoach_chi();
            }
        });

        list_KhChi.clear();
        list_KhChi = kHchiDAO.getAll_Ke_hoach_chi();
        KhCHiAdapter adapter = new KhCHiAdapter( getActivity() , list_KhChi );
        lv_ds_ke_hoach_Chi.setAdapter(adapter);

        try {
            lv_ds_ke_hoach_Chi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    update_Ke_Hoach_Chi(position);
                }
            });
        } catch (Exception ex){
            Log.e("\t\tChi Fragment : Error\t" , ex.toString());
        }

        return view;
    }

    private void add_Ke_hoach_chi() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_khoan_thu , null);

        TextInputLayout edt_ma_Du_Chi, edt_so_Tien_Du_Chi , edt_ngay_Du_Chi, edt_Chu_Thich;

        Button btn_huy = view.findViewById(R.id.btn_huy);
        Button btn_add_ke_hoach_chi = view.findViewById(R.id.btn_add_Khoan_Thu);
        Button btn_ngay_nhan_tien = view.findViewById(R.id.btn_ngay_nhan_tien);
        edt_ma_Du_Chi  = view.findViewById(R.id.edt_ma_thu_nhap);
        Spinner spinner_userName = view.findViewById(R.id.spinner_userName);
        edt_so_Tien_Du_Chi  = view.findViewById(R.id.edt_so_Tien_THu);
        edt_ngay_Du_Chi  = view.findViewById(R.id.edt_ngay_nhan_tien);
        edt_Chu_Thich  = view.findViewById(R.id.edt_Chu_Thich_khoan_thu);
        spinner_userName.setSelection(0);
        get_nguoi_Dung(spinner_userName);
        edt_ma_Du_Chi.setHint("Mã Dự Chi");
        edt_so_Tien_Du_Chi.setHint("Số Tiền Dự Chi");
        edt_ngay_Du_Chi.setHint("Ngày Dự Chi");


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder( getActivity() );
        builder.setView(view).setTitle("Thêm Kế Hoạch Chi");
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        btn_ngay_nhan_tien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_time(edt_ngay_Du_Chi);
            }
        });

        btn_add_ke_hoach_chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma_Du_Chi , userName , so_Tien_Du_Chi , ngay_Du_Chi , chu_Thich;
                ma_Du_Chi = edt_ma_Du_Chi.getEditText().getText().toString();
                userName = spinner_userName.getSelectedItem().toString();
                so_Tien_Du_Chi = edt_so_Tien_Du_Chi.getEditText().getText().toString();
                ngay_Du_Chi = edt_ngay_Du_Chi.getEditText().getText().toString();
                chu_Thich = edt_Chu_Thich.getEditText().getText().toString();
                String regex_so = "[0-9]+";

                if (  ma_Du_Chi.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Mã Dự Chi");

                } else if (  so_Tien_Du_Chi.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Số Tiền Dự Chi");

                } else if ( ! so_Tien_Du_Chi.matches(regex_so) ){

                    dialog_chung(0, getActivity(), "Số tiền phải nhập dạng Số");

                }
                else if (  ngay_Du_Chi.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải chọn Ngày Dự Chi");

                } else if ( (Integer.parseInt(so_Tien_Du_Chi)) == 0
                            || (Integer.parseInt(so_Tien_Du_Chi)) < 0){
                    dialog_chung(0, getActivity(), "Số Tiền phải > 0");
                } else {
                    try {
                        KHchi kHchi = new KHchi(
                                "KHC_" + ma_Du_Chi ,
                                userName ,
                                so_Tien_Du_Chi ,
                                ngay_Du_Chi ,
                                chu_Thich
                        );

                        String[] get_tk = kHchi.getUserName().split(" | ");
                        String get_user = get_tk[0];

                        NguoiDung nd = list_ND.get( get_vi_tri(list_ND , get_user) );

                        Integer so_tien_CHi = Integer.parseInt( so_Tien_Du_Chi );
                        Integer tong_tien_TK = Integer.parseInt( nd.getTongSoTien() );

                        int so_tien =  tong_tien_TK - so_tien_CHi;

                        if ( so_tien_CHi > tong_tien_TK ){

                            dialog_chung(0, getActivity(), "Bạn Không đủ tiền chi trả");

                        } else if ( so_tien_CHi == 0 ){

                            dialog_chung(0, getActivity(), "Số tiền phải > 0");

                        } else if ( kHchiDAO.inser_ke_hoach_chi( kHchi ) > 0) {

                            nd.setTongSoTien(String.valueOf(so_tien));
                            nguoiDungDAO.updateNguoiDung(nd);
                            kHchi.setUserName(nd.toString());
                            //                        boolean kq = userName.equals( list_ND.get( get_vi_tri(list_ND , get_user) ).toString() );
//                        Log.e("\t\t" + userName , nd.toString()
//                                + " | " + String.valueOf( " " + kq +" -- ") + get_user + "\t");

                            if ( kHchiDAO.update_ke_hoach_chi( kHchi ) > 0 ){

                            }

                            dialog_chung(1, getActivity(), "Thêm khoản Chi Thành Công");
                            dialog.dismiss();

                            list_KhChi.clear();
                            list_KhChi = kHchiDAO.getAll_Ke_hoach_chi();
                            KhCHiAdapter adapter = new KhCHiAdapter( getActivity() , list_KhChi );
                            lv_ds_ke_hoach_Chi.setAdapter(adapter);

                        } else if ( kHchiDAO.check_ID_KhChi( kHchi ) ) {

                            dialog_chung(0, getActivity(), "Kế Hoạch Chi đã tồn tại !\n\nVui lòng nhập mã khác.");

                        } else {

                            dialog_chung(1, getActivity(), "Thêm Thất Bại");
                        }

                    } catch (Exception ex) {
                        Log.e("Error Đăng Kí  : \t\t", ex.toString());
                    }
                }

            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                list_KhChi.clear();
                list_KhChi = kHchiDAO.getAll_Ke_hoach_chi();
                KhCHiAdapter adapter = new KhCHiAdapter( getActivity() , list_KhChi );
                lv_ds_ke_hoach_Chi.setAdapter(adapter);
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


    private void update_Ke_Hoach_Chi(Integer position) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_khoan_thu , null);

        TextInputLayout edt_ma_Du_Chi, edt_so_Tien_Du_Chi, edt_ngay_Du_Chi, edt_Chu_Thich;

        Button btn_huy = view.findViewById(R.id.btn_huy);
        Button btn_add_ke_hoach_chi = view.findViewById(R.id.btn_add_Khoan_Thu);
        Button btn_ngay_nhan_tien = view.findViewById(R.id.btn_ngay_nhan_tien);
        edt_ma_Du_Chi  = view.findViewById(R.id.edt_ma_thu_nhap);
        Spinner spinner_userName = view.findViewById(R.id.spinner_userName);
        edt_so_Tien_Du_Chi  = view.findViewById(R.id.edt_so_Tien_THu);
        edt_ngay_Du_Chi  = view.findViewById(R.id.edt_ngay_nhan_tien);
        edt_Chu_Thich  = view.findViewById(R.id.edt_Chu_Thich_khoan_thu);
        spinner_userName.setSelection(0);
        get_nguoi_Dung(spinner_userName);
        edt_ma_Du_Chi.setHint("Mã Dự Chi");
        edt_so_Tien_Du_Chi.setHint("Số Tiền Dự Chi");
        edt_ngay_Du_Chi.setHint("Ngày Dự Chi");

        list_KhChi.clear();
        list_KhChi = kHchiDAO.getAll_Ke_hoach_chi();
        edt_ma_Du_Chi.getEditText().setText( list_KhChi.get(position).getMaDuChi() );
        edt_ma_Du_Chi.getEditText().setEnabled(false);
        spinner_userName.setSelection( getIndex( spinner_userName , list_KhChi.get(position).getUserName() ) );
        edt_so_Tien_Du_Chi.getEditText().setText( list_KhChi.get(position).getSoTienDuChi() );
        edt_ngay_Du_Chi.getEditText().setText( list_KhChi.get(position).getNgayDuChi() );
        edt_Chu_Thich.getEditText().setText( list_KhChi.get(position).getChuThich() );
        btn_add_ke_hoach_chi.setText("Cập Nhật");

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setView(view).setTitle("Cập Nhật Kế Hoạch Chi");
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        btn_ngay_nhan_tien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_time(edt_ngay_Du_Chi);
            }
        });

        btn_add_ke_hoach_chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma_Du_Chi , userName , so_Tien_Du_Chi , ngay_Du_Chi , chu_Thich;
                ma_Du_Chi = edt_ma_Du_Chi.getEditText().getText().toString();
                userName = spinner_userName.getSelectedItem().toString();
                so_Tien_Du_Chi = edt_so_Tien_Du_Chi.getEditText().getText().toString();
                ngay_Du_Chi = edt_ngay_Du_Chi.getEditText().getText().toString();
                chu_Thich = edt_Chu_Thich.getEditText().getText().toString();
                String regex_so = "[0-9]+";

                if (  ma_Du_Chi.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Mã Dự Chi");

                } else if (  so_Tien_Du_Chi.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải nhập Số Tiền Dự Chi");

                } else if ( ! so_Tien_Du_Chi.matches(regex_so) ){

                    dialog_chung(0, getActivity(), "Số tiền phải dạng nhập Số");

                }
                else if (  ngay_Du_Chi.isEmpty() ){

                    dialog_chung(0, getActivity(), "Phải chọn Ngày Dự Chi");

                } else {
                    try {
                        KHchi kHchi = new KHchi(
                                ma_Du_Chi ,
                                userName ,
                                so_Tien_Du_Chi ,
                                ngay_Du_Chi ,
                                chu_Thich
                        );

                        if ( kHchiDAO.update_ke_hoach_chi( kHchi ) > 0) {

                            dialog_chung(1, getActivity(), "Cập Nhật Kế Hoạch Chi Thành Công");
                            dialog.dismiss();

                            list_KhChi.clear();
                            list_KhChi = kHchiDAO.getAll_Ke_hoach_chi();
                            KhCHiAdapter adapter = new KhCHiAdapter( getActivity() , list_KhChi );
                            lv_ds_ke_hoach_Chi.setAdapter(adapter);

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

                list_KhChi.clear();
                list_KhChi = kHchiDAO.getAll_Ke_hoach_chi();
                KhCHiAdapter adapter = new KhCHiAdapter( getActivity() , list_KhChi );
                lv_ds_ke_hoach_Chi.setAdapter(adapter);
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

}