package com.example.project_1.Thu;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_1.Adapter.DSnguoiDungAdapter;
import com.example.project_1.Adapter.ThuAdapter;
import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.DAO.ThuDAO;
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

public class ThuFragment extends Fragment {

    private ThuDAO thuDAO;
    private NguoiDungDAO nguoiDungDAO;
    private ListView lv_ds_Thu;
    private List<Thu> listThu = new ArrayList<>();
    private FloatingActionButton fbtn_add_khoan_thu;
    private List<NguoiDung> list_ND = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    final Calendar calendar = Calendar.getInstance();
    private String user = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thu, container, false);

        thuDAO = new ThuDAO( getActivity() );
        nguoiDungDAO = new NguoiDungDAO( getActivity() );
        lv_ds_Thu = view.findViewById(R.id.lv_ds_Thu);
        fbtn_add_khoan_thu = view.findViewById(R.id.fbtn_add_khoan_thu);

        fbtn_add_khoan_thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_Khoan_thu();
            }
        });

        listThu.clear();
        listThu = thuDAO.getAll_Khoan_Thu();
        ThuAdapter adapter = new ThuAdapter(getActivity() , listThu);
        lv_ds_Thu.setAdapter(adapter);

        try {
            lv_ds_Thu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    update_Khoan_thu(position);
                }
            });
        } catch (Exception ex){
            Log.e("\t\t\tThuFragment : Error\t" , ex.toString());
        }
        return view;
    }

    private void add_Khoan_thu() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_khoan_thu , null);

        TextInputLayout edt_ma_thu_nhap, edt_so_Tien_THu, edt_ngay_nhan_tien, edt_Chu_Thich;

        Button btn_huy = view.findViewById(R.id.btn_huy);
        Button btn_add_Khoan_Thu = view.findViewById(R.id.btn_add_Khoan_Thu);
        ImageButton btn_ngay_nhan_tien = view.findViewById(R.id.btn_ngay_nhan_tien);
        edt_ma_thu_nhap  = view.findViewById(R.id.edt_ma_thu_nhap);
        Spinner spinner_userName = view.findViewById(R.id.spinner_userName);
        edt_so_Tien_THu  = view.findViewById(R.id.edt_so_Tien_THu);
        edt_ngay_nhan_tien  = view.findViewById(R.id.edt_ngay_nhan_tien);
        edt_Chu_Thich  = view.findViewById(R.id.edt_Chu_Thich_khoan_thu);
        spinner_userName.setSelection(0);
        get_nguoi_Dung(spinner_userName);
        edt_ma_thu_nhap.setEnabled(false);


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setView(view).setTitle("Th??m Kho???n Thu");
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        btn_ngay_nhan_tien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_time(edt_ngay_nhan_tien);
            }
        });

        btn_add_Khoan_Thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ma_thu_nhap , userName , so_Tien_thu , ngay_nhan_tien , chu_Thich;
                    ma_thu_nhap = edt_ma_thu_nhap.getEditText().getText().toString();
                    userName = spinner_userName.getSelectedItem().toString();
                    so_Tien_thu = edt_so_Tien_THu.getEditText().getText().toString();
                    ngay_nhan_tien = edt_ngay_nhan_tien.getEditText().getText().toString();
                    chu_Thich = edt_Chu_Thich.getEditText().getText().toString();
                    String regex_so = "[0-9]+";

                    if (  so_Tien_thu.isEmpty() ){

                        dialog_chung(0, getActivity(), "Ph???i nh???p S??? Ti???n");

                    } else if ( ! so_Tien_thu.matches(regex_so) ){

                        dialog_chung(0, getActivity(), "S??? ti???n ph???i nh???p S???");

                    }
                    else if (  ngay_nhan_tien.isEmpty() ){

                        dialog_chung(0, getActivity(), "Ph???i ch???n Ng??y Nh???n Ti???n");

                    } else if ( so_Tien_thu.length() > 10){

                        dialog_chung(0, getActivity(), "S??? Ti???n ph???i < 1 t???");

                    }  else if ( (Integer.parseInt(so_Tien_thu)) == 0
                            || (Integer.parseInt(so_Tien_thu)) < 0 ){

                        dialog_chung(0, getActivity(), "S??? Ti???n ph???i > 0");
                    } else {

                        Thu thu = new Thu(
                                "TN_" + System.currentTimeMillis(),
                                userName,
                                so_Tien_thu,
                                ngay_nhan_tien,
                                chu_Thich
                        );

                        if ( thuDAO.inser_Khoan_Thu(thu) > 0) {

                            String[] get_tk = thu.getUserName().split(" | ");
                            String get_user = get_tk[0];
                            user = get_user;

                            NguoiDung nd = list_ND.get( get_vi_tri(list_ND , get_user) );

                            Integer so_tien = Integer.parseInt(thu.getSoTienThu()) + Integer.parseInt(nd.getTongSoTien());

                            nd.setTongSoTien(String.valueOf(so_tien));
                            nguoiDungDAO.updateNguoiDung(nd);
                            thu.setUserName(nd.toString());
                            //                        boolean kq = userName.equals( list_ND.get( get_vi_tri(list_ND , get_user) ).toString() );
//                        Log.e("\t\t" + userName , nd.toString()
//                                + " | " + String.valueOf( " " + kq +" -- ") + get_user + "\t");

                            if ( thuDAO.update_Khoan_Thu(thu) > 0 ){

                            }

                            dialog_chung(1, getActivity(), "Th??m kho???n thu Th??nh C??ng");
                            dialog.dismiss();

                            listThu.clear();
                            listThu = thuDAO.getAll_Khoan_Thu();
                            ThuAdapter adapter = new ThuAdapter(getActivity() , listThu);
                            lv_ds_Thu.setAdapter(adapter);

                        } else if ( thuDAO.check_Khoan_Thu(thu) ) {

                            dialog_chung(0, getActivity(), "Kho???n Thu ???? t???n t???i !\n\nVui l??ng nh???p m?? kh??c.");

                        } else {

                            dialog_chung(1, getActivity(), "Th??m Th???t B???i");
                        }
                    }
                } catch (Exception ex){
                    Log.e("thu fragment 203" , ex.toString() );
                    list_ND.clear();
                    list_ND = nguoiDungDAO.getAllNguoiDung();

                    list_ND.get( get_vi_tri(list_ND , user) ).setTongSoTien("0");
                    nguoiDungDAO.updateNguoiDung( list_ND.get( get_vi_tri(list_ND , user) ) );
                }
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                listThu.clear();
                listThu = thuDAO.getAll_Khoan_Thu();
                ThuAdapter adapter = new ThuAdapter(getActivity() , listThu);
                lv_ds_Thu.setAdapter(adapter);
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
        list_ND.clear();
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

    private void update_Khoan_thu(Integer position) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_khoan_thu , null);

        TextInputLayout edt_ma_thu_nhap, edt_so_Tien_THu, edt_ngay_nhan_tien, edt_Chu_Thich;

        Button btn_huy = view.findViewById(R.id.btn_huy);
        Button btn_add_Khoan_Thu = view.findViewById(R.id.btn_add_Khoan_Thu);
        ImageButton btn_ngay_nhan_tien = view.findViewById(R.id.btn_ngay_nhan_tien);
        edt_ma_thu_nhap  = view.findViewById(R.id.edt_ma_thu_nhap);
        Spinner spinner_userName = view.findViewById(R.id.spinner_userName);
        edt_so_Tien_THu  = view.findViewById(R.id.edt_so_Tien_THu);
        edt_ngay_nhan_tien  = view.findViewById(R.id.edt_ngay_nhan_tien);
        edt_Chu_Thich  = view.findViewById(R.id.edt_Chu_Thich_khoan_thu);
        spinner_userName.setSelection(0);
        get_nguoi_Dung(spinner_userName);
        edt_so_Tien_THu.getEditText().setEnabled(false);

        listThu.clear();
        listThu = thuDAO.getAll_Khoan_Thu();
        edt_ma_thu_nhap.getEditText().setText( listThu.get(position).getMaThuNhap() );
        edt_ma_thu_nhap.getEditText().setEnabled(false);
        spinner_userName.setSelection( getIndex( spinner_userName , listThu.get(position).getUserName() ) );
        edt_so_Tien_THu.getEditText().setText( listThu.get(position).getSoTienThu() );
        edt_ngay_nhan_tien.getEditText().setText( listThu.get(position).getNgayNhanTien() );
        edt_Chu_Thich.getEditText().setText( listThu.get(position).getChuThich() );
        btn_add_Khoan_Thu.setText("C???p Nh???t");

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setView(view).setTitle("C???p Nh???t Kho???n Thu");
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        btn_ngay_nhan_tien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_time(edt_ngay_nhan_tien);
            }
        });

        btn_add_Khoan_Thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma_thu_nhap , userName , so_Tien_thu , ngay_nhan_tien , chu_Thich;
                ma_thu_nhap = edt_ma_thu_nhap.getEditText().getText().toString();
                userName = spinner_userName.getSelectedItem().toString();
                so_Tien_thu = edt_so_Tien_THu.getEditText().getText().toString();
                ngay_nhan_tien = edt_ngay_nhan_tien.getEditText().getText().toString();
                chu_Thich = edt_Chu_Thich.getEditText().getText().toString();
                String regex_so = "[0-9]+";

                if (  so_Tien_thu.isEmpty() ){

                    dialog_chung(0, getActivity(), "Ph???i nh???p S??? Ti???n");

                } else if ( ! so_Tien_thu.matches(regex_so) ){

                    dialog_chung(0, getActivity(), "S??? ti???n ph???i nh???p S???");

                }
                else if (  ngay_nhan_tien.isEmpty() ){

                    dialog_chung(0, getActivity(), "Ph???i ch???n Ng??y Nh???n Ti???n");

                } else if ( so_Tien_thu.length() > 10){

                    dialog_chung(0, getActivity(), "S??? Ti???n ph???i < 1 t???");

                } else {
                    try {
                        Thu thu = new Thu(
                                ma_thu_nhap,
                                userName,
                                so_Tien_thu,
                                ngay_nhan_tien,
                                chu_Thich
                        );

                        if ( thuDAO.update_Khoan_Thu(thu) > 0) {

                            dialog_chung(1, getActivity(), "C???p Nh???t Kho???n Thu Th??nh C??ng");
                            dialog.dismiss();

                            listThu.clear();
                            listThu = thuDAO.getAll_Khoan_Thu();
                            ThuAdapter adapter = new ThuAdapter(getActivity() , listThu);
                            lv_ds_Thu.setAdapter(adapter);

                        } else {

                            dialog_chung(1, getActivity(), "C???p Nh???t Th???t B???i");
                        }

                    } catch (Exception ex) {
                        Log.e("Error ????ng K??  : \t\t", ex.toString());
                    }
                }

            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                listThu.clear();
                listThu = thuDAO.getAll_Khoan_Thu();
                ThuAdapter adapter = new ThuAdapter(getActivity() , listThu);
                lv_ds_Thu.setAdapter(adapter);
            }
        });

        dialog.show();
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
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