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
import android.widget.ImageButton;
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
        View view = inflater.inflate(R.layout.add_khoan_thu , null);

        TextInputLayout edt_ma_Tiet_Kiem, edt_so_Tien_Tiet_Kiem , edt_ngay_Tiet_Kiem, edt_Chu_Thich;

        Button btn_huy = view.findViewById(R.id.btn_huy);
        Button btn_add_Tiet_kiem = view.findViewById(R.id.btn_add_Khoan_Thu);
        ImageButton btn_ngay_nhan_tien = view.findViewById(R.id.btn_ngay_nhan_tien);
//        CheckBox cbk_Status = view.findViewById(R.id.ckb_tiet_Kiem);

        edt_ma_Tiet_Kiem  = view.findViewById(R.id.edt_ma_thu_nhap);
        Spinner spinner_userName = view.findViewById(R.id.spinner_userName);
        edt_so_Tien_Tiet_Kiem  = view.findViewById(R.id.edt_so_Tien_THu);
        edt_ngay_Tiet_Kiem  = view.findViewById(R.id.edt_ngay_nhan_tien);
        edt_Chu_Thich  = view.findViewById(R.id.edt_Chu_Thich_khoan_thu);
        spinner_userName.setSelection(0);
        get_nguoi_Dung(spinner_userName);
        edt_ma_Tiet_Kiem.setHint("M?? Ti???t Ki???m");
        edt_so_Tien_Tiet_Kiem.setHint("S??? Ti???n Ti???t Ki???m");
        edt_ngay_Tiet_Kiem.setHint("Ng??y Ti???t Ki???m");
//        cbk_Status.setText("Ch??a Ti???t Ki???m");
        edt_ma_Tiet_Kiem.setEnabled(false);
//        set_Status(cbk_Status);


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder( getActivity() );
        builder.setView(view).setTitle("Th??m Ti???t Ki???m");
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
//                String status = String.valueOf( cbk_Status.isChecked() );
                String regex_so = "[0-9]+";

                if (  so_Tien_Tiet_Kiem.isEmpty() ){

                    dialog_chung(0, getActivity(), "Ph???i nh???p S??? Ti???n Ti???t Ki???m");

                } else if ( ! so_Tien_Tiet_Kiem.matches(regex_so) ){

                    dialog_chung(0, getActivity(), "S??? ti???n ph???i nh???p d???ng S???");

                }
                else if (  ngay_Tiet_Kiem.isEmpty() ){

                    dialog_chung(0, getActivity(), "Ph???i ch???n Ng??y Ti???t Ki???m");

                } else if ( so_Tien_Tiet_Kiem.length() > 10 ){

                    dialog_chung(0, getActivity(), "S??? ti???n ph???i < 1 t???");

                } else if ( (Integer.parseInt(so_Tien_Tiet_Kiem)) == 0
                            || (Integer.parseInt(so_Tien_Tiet_Kiem)) < 0){

                    dialog_chung(0, getActivity(), "S??? ti???n Ph???i > 0");

                } else {
                    try {
                        TietKiem tietKiem = new TietKiem(
                                "TK_" + System.currentTimeMillis() ,
                                userName ,
                                so_Tien_Tiet_Kiem ,
                                ngay_Tiet_Kiem ,
                                chu_Thich ,
                                ""
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

                            dialog_chung(0, getActivity(), "B???n Kh??ng ????? ti???n chi tr???");

                        } else if ( so_tien_CHi == 0 ||
                                    so_tien < 0){

                            dialog_chung(0, getActivity(), "S??? ti???n chi ph???i > 0");

                        } else if ( tietKiemDAO.inser_Tiet_Kiem( tietKiem ) > 0) {

                            nd.setTongSoTien(String.valueOf(so_tien));
                            nguoiDungDAO.updateNguoiDung(nd);
                            tietKiem.setUserName(nd.toString());
                            //                        boolean kq = userName.equals( list_ND.get( get_vi_tri(list_ND , get_user) ).toString() );
//                        Log.e("\t\t" + userName , nd.toString()
//                                + " | " + String.valueOf( " " + kq +" -- ") + get_user + "\t");

                            if ( tietKiemDAO.update_Tiet_Kiem( tietKiem ) > 0 ){

                            }

                            dialog_chung(1, getActivity(), "Th??m Ti???t Ki???m Th??nh C??ng");
                            dialog.dismiss();

                            list_Tiet_Kiem.clear();
                            list_Tiet_Kiem = tietKiemDAO.getAll_Tiet_Kiem();
                            TietKiemAdapter adapter = new TietKiemAdapter( getActivity() , list_Tiet_Kiem);
                            lv_ds_Tiet_Kiem.setAdapter(adapter);

                        } else if ( tietKiemDAO.chech_Tiet_kiem( tietKiem ) ) {

                            dialog_chung(0, getActivity(), "M?? Ti???t Ki???m ???? t???n t???i !\n\nVui l??ng nh???p m?? kh??c.");

                        } else {

                            dialog_chung(1, getActivity(), "Th??m Th???t B???i");
                        }

                    } catch (Exception ex) {
                        Log.e("Error Th??m  : \t\t", ex.toString());
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
                builder.setTitle("Th??ng B??o").setMessage(tb);
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
        View view = inflater.inflate(R.layout.add_khoan_thu , null);

        TextInputLayout edt_ma_Tiet_Kiem, edt_so_Tien_Tiet_Kiem, edt_ngay_Tiet_Kiem, edt_Chu_Thich;

        Button btn_huy = view.findViewById(R.id.btn_huy);
        Button btn_update_Tiet_Kiem = view.findViewById(R.id.btn_add_Khoan_Thu);
        ImageButton btn_ngay_nhan_tien = view.findViewById(R.id.btn_ngay_nhan_tien);
//        CheckBox cbk_Status = view.findViewById(R.id.ckb_tiet_Kiem);

        edt_ma_Tiet_Kiem  = view.findViewById(R.id.edt_ma_thu_nhap);
        Spinner spinner_userName = view.findViewById(R.id.spinner_userName);
        edt_so_Tien_Tiet_Kiem  = view.findViewById(R.id.edt_so_Tien_THu);
        edt_ngay_Tiet_Kiem  = view.findViewById(R.id.edt_ngay_nhan_tien);
        edt_Chu_Thich  = view.findViewById(R.id.edt_Chu_Thich_khoan_thu);
        spinner_userName.setSelection(0);
        get_nguoi_Dung(spinner_userName);
        edt_ma_Tiet_Kiem.setHint("M?? Ti???t Ki???m");
        edt_so_Tien_Tiet_Kiem.setHint("S??? Ti???n Ti???t Ki???m");
        edt_ngay_Tiet_Kiem.setHint("Ng??y Ti???t Ki???m");
//        cbk_Status.setText("Ch??a Ti???t Ki???m");
        edt_ma_Tiet_Kiem.setEnabled(false);
//        set_Status(cbk_Status);

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

//        if ( Boolean.parseBoolean( list_Tiet_Kiem.get(position).getStatus() ) ){
//            cbk_Status.setChecked( Boolean.parseBoolean( list_Tiet_Kiem.get(position).getStatus() ) );
//            cbk_Status.setText("???? Ti???t Ki???m");
//        } else {
//            cbk_Status.setChecked( Boolean.parseBoolean( list_Tiet_Kiem.get(position).getStatus() ) );
//            cbk_Status.setText("Ch??a Ti???t Ki???m");
//        }
        btn_update_Tiet_Kiem.setText("C???p Nh???t");

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setView(view).setTitle("C???p Nh???t Ti???t Ki???m");
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
//                String status = String.valueOf( cbk_Status.isChecked() );
                String regex_so = "[0-9]+";

                if (  so_Tien_Tiet_Kiem.isEmpty() ){

                    dialog_chung(0, getActivity(), "Ph???i nh???p S??? Ti???n Ti???t Ki???m");

                } else if ( ! so_Tien_Tiet_Kiem.matches(regex_so) ){

                    dialog_chung(0, getActivity(), "S??? ti???n ph???i nh???p d???ng S???");

                }
                else if (  ngay_Tiet_Kiem.isEmpty() ){

                    dialog_chung(0, getActivity(), "Ph???i ch???n Ng??y Ti???t Ki???m");

                } else if ( so_Tien_Tiet_Kiem.length() > 10 ){

                    dialog_chung(0, getActivity(), "S??? ti???n ph???i < 1 t???");

                } else if ( (Integer.parseInt(so_Tien_Tiet_Kiem)) == 0
                        || (Integer.parseInt(so_Tien_Tiet_Kiem)) < 0){

                    dialog_chung(0, getActivity(), "S??? ti???n Ph???i > 0");

                } else {
                    try {
                        TietKiem tietKiem = new TietKiem(
                                ma_Tiet_Kiem ,
                                userName ,
                                so_Tien_Tiet_Kiem ,
                                ngay_Tiet_Kiem ,
                                chu_Thich ,
                                ""
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

                            dialog_chung(0, getActivity(), "B???n Kh??ng ????? ti???n chi tr???");

                        } else if ( so_tien_CHi == 0 ||
                                so_tien < 0){

                            dialog_chung(0, getActivity(), "S??? ti???n chi ph???i > 0");

                        } else if ( tietKiemDAO.update_Tiet_Kiem( tietKiem ) > 0) {

                            nd.setTongSoTien(String.valueOf(so_tien));
                            nguoiDungDAO.updateNguoiDung(nd);
                            tietKiem.setUserName(nd.toString());
                            //                        boolean kq = userName.equals( list_ND.get( get_vi_tri(list_ND , get_user) ).toString() );
//                        Log.e("\t\t" + userName , nd.toString()
//                                + " | " + String.valueOf( " " + kq +" -- ") + get_user + "\t");

//                            if ( tietKiemDAO.update_Tiet_Kiem( tietKiem ) > 0 ){
//
//                            }

                            dialog_chung(1, getActivity(), "C???p nh???t Ti???t Ki???m Th??nh C??ng");
                            dialog.dismiss();

                            list_Tiet_Kiem.clear();
                            list_Tiet_Kiem = tietKiemDAO.getAll_Tiet_Kiem();
                            TietKiemAdapter adapter = new TietKiemAdapter( getActivity() , list_Tiet_Kiem);
                            lv_ds_Tiet_Kiem.setAdapter(adapter);

                        } else {

                            dialog_chung(1, getActivity(), "C???p nh???t Th???t B???i");
                        }

                    } catch (Exception ex) {
                        Log.e("Error Th??m  : \t\t", ex.toString());
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
                    cbk.setText("???? Ti???t Ki???m");
                } else {
                    cbk.setText("Ch??a Ti???t Ki???m");
                }
            }
        });
    }
}