package com.example.project_1.NguoiDung;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_1.Adapter.DSnguoiDungAdapter;
import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.HomeActivity;
import com.example.project_1.Models.NguoiDung;
import com.example.project_1.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DSnguoiDungFragment extends Fragment {

    private ListView lv_ds;
    private NguoiDungDAO nguoiDungDAO;
    private List<NguoiDung> list = new ArrayList<>();
//    private HomeActivity homeActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_d_snguoi_dung, container, false);

        lv_ds = layout.findViewById(R.id.lv_user);

        nguoiDungDAO = new NguoiDungDAO( getActivity() );
        list = nguoiDungDAO.getAllNguoiDung();
        DSnguoiDungAdapter adapter = new DSnguoiDungAdapter( getActivity() , list);
        lv_ds.setAdapter(adapter);

        lv_ds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edit_User(position);
            }
        });

        return layout;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//
//        if (context instanceof HomeActivity){
//            this.homeActivity = (HomeActivity) context;
//        }
//    }
private void edit_User(Integer i) {
    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.add_tk , null);

    TextInputLayout edt_ho_ten, edt_sdt, edt_tk, edt_mk, edt_lai_mk;
    Button btn_add_tk = view.findViewById(R.id.btn_add_tk);
    btn_add_tk.setText("chỉnh sửa");
    edt_ho_ten = view.findViewById(R.id.edt_add_ho_ten);
    edt_sdt = view.findViewById(R.id.edt_add_sdt);
    edt_tk = view.findViewById(R.id.edt_add_tk);
    edt_mk = view.findViewById(R.id.edt_add_mk);
    edt_lai_mk = view.findViewById(R.id.edt_add_nhap_lai_mk);

    list.clear();
    list = nguoiDungDAO.getAllNguoiDung();
    edt_ho_ten.getEditText().setText( list.get(i).getHoTen() );
    edt_sdt.getEditText().setText( list.get(i).getPhone() );
    edt_tk.getEditText().setText( list.get(i).getUserName() );
    edt_tk.setEnabled(false);
    edt_mk.getEditText().setText( list.get(i).getPassword() );
    edt_lai_mk.getEditText().setText( list.get(i).getPassword() );

    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
    builder.setView(view).setTitle("Đổi mật khẩu");

    androidx.appcompat.app.AlertDialog dialog = builder.create();

    btn_add_tk.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String ho_ten, sdt, tk, mk, lai_mk;
            ho_ten = edt_ho_ten.getEditText().getText().toString();
            sdt = edt_sdt.getEditText().getText().toString();
            tk = edt_tk.getEditText().getText().toString();
            mk = edt_mk.getEditText().getText().toString();
            lai_mk = edt_lai_mk.getEditText().getText().toString();
            String regex_sdt = "[0-9]{9,10}";

            if (  ho_ten.isEmpty() ){
                dialog_chung(0, getActivity() , "Phải nhập Họ Tên");
            } else if (  sdt.isEmpty() ){
                dialog_chung(0, getActivity() , "Phải nhập Số Điện Thoại");
            } else if (  tk.isEmpty() ){
                dialog_chung(0, getActivity() , "Phải nhập Tài Khoản");
            } else if (  mk.isEmpty() ){
                dialog_chung(0, getActivity() , "Phải nhập Mật Khẩu");
            } else if (  lai_mk.isEmpty() ){
                dialog_chung(0, getActivity() , "Hãy nhập lại Mật Khẩu !!!");
            } else if (sdt.length() > 10) {
                dialog_chung(0, getActivity() , "Số Điện Thoại \nChỉ được nhập 10 số");
            } else if ( !mk.equals(lai_mk)) {
                dialog_chung(0, getActivity() , "Mật khẩu không khớp. Vui lòng Nhập lại !!!");
            } else if (sdt.length() < 10){
                dialog_chung(0 , getActivity()  , "Số điện thoại phải là dạng 10 số");
            } else if ( !sdt.matches(regex_sdt) ){
                dialog_chung(0 , getActivity() , "Số Điện Thoại phải nhập SỐ ...");
            } else {

                try {
                    NguoiDung user = new NguoiDung(
                            tk , mk , ho_ten , "" , sdt
                    );

                    if (nguoiDungDAO.updateNguoiDung(user) > 0) {

                        dialog_chung(1 , getActivity() , "Đổi mật khẩu thành công");
                        dialog.dismiss();

                        list.clear();
                        list = nguoiDungDAO.getAllNguoiDung();
                        DSnguoiDungAdapter adapter = new DSnguoiDungAdapter( getActivity() , list);
                        lv_ds.setAdapter(adapter);
                    } else if ( nguoiDungDAO.checkLogin(user.getUserName() , user.getPassword()) > 0){
                        dialog_chung(0 , getActivity() , "Tài Khoản đã tồn tại !\nVui lòng chọn tài khoản khác.");
                    } else {

                        dialog_chung(1 , getActivity() , "Chỉnh sửa thất bại");
                    }

                } catch (Exception ex) {
                    Log.e("Error Đăng Kí  : \t\t", ex.toString());
                }
            }

            Log.e("show  : ", ho_ten + "\n" + sdt + "\n" + tk
                    + "\n" + mk + "\n" + lai_mk + "\n\t\t\t\t\t" + sdt.matches(regex_sdt) );
        }
    });

    dialog.show();
}

    private void dialog_chung(Integer so, Context context, String tb) {
        switch (so) {
            case 0:
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setTitle("Thông Báo").setMessage(tb);
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case 1:
                Toast.makeText(context, tb, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}